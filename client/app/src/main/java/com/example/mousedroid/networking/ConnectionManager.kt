package com.example.mousedroid.networking

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.ceil
import kotlin.math.min

class ConnectionManager private constructor() : Connection.Listener {

    private val TAG = "Mousedroid"

    companion object {
        @Volatile private var instance: ConnectionManager? = null

        fun getInstance(): ConnectionManager {
            synchronized(this) {
                return instance ?: ConnectionManager().also { instance = it }
            }
        }

        fun getInstance(connectionStateCallback: ConnectionStateCallback): ConnectionManager {
            synchronized(this) {
                if(instance == null) {
                    instance = ConnectionManager()
                }
                instance!!.setConnectionStateCallback(connectionStateCallback)
                return instance!!
            }
        }
    }

    enum class Mode {
        USB,
        WIFI
    }

    private var connectionStateCallback: ConnectionStateCallback? = null
    private var onBytesReceivedCallback: ((buffer: ByteArray, bytes: Int) -> Unit)? = null

    private var tcpConn: TCPConnection? = null
    private var udpConn: UDPConnection? = null

    /**
     * Active connection. Prioritize UDP connection if available
     * otherwise fall back to the TCP Conneciton
     */
    private val connection
        get() = (udpConn ?: tcpConn) as Connection

    private var streamingEnabled = false

    interface ConnectionStateCallback {
        fun onConnectionInitiated() { }
        fun onConnectionSuccessful(connectionMode: Mode) { }
        fun onConnectionFailed(connectionMode: Mode) { }
        fun onDisconnected() { }
    }

    /**
     * Wrapper around a socket to manage the TCP connection.
     * Using it only to send the video stream. No packet receiving implementation.
     */
    private inner class UDPConnection(
        ipAddress: String,
        private val port: Int,
    ) : Connection() {

        private val address: InetAddress = InetAddress.getByName(ipAddress)

        override val maxPacketSize = 65472

        private var socket: DatagramSocket

        init {
            try {
                socket = DatagramSocket()
                socket.connect(address, port)
                socket.sendBufferSize = 921600

                Log.d(TAG, "UDP Connected to $ipAddress:$port")
            } catch (e: Exception) {
                throw ConnectionFailedException("$ipAddress:$port")
            }
        }

        override fun send(bytes: ByteArray) {
            try {
                socket.send(DatagramPacket(bytes, bytes.size, address, port))
                //socket.receive(DatagramPacket(ByteArray(5), 5, address, port))
            } catch (_: Exception) {}
        }

        override fun send(bytes: ByteArray, size: Int) {
            try {
                socket.send(DatagramPacket(bytes, size, address, port))
                //socket.receive(DatagramPacket(ByteArray(5), 5, address, port))
            } catch (_: Exception) {}
        }

        override fun close() {
            socket.close()
        }
    }

    /**
     * Wrapper around a socket to manage the TCP connection
     * @param ipAddress Remote endpoint's IPv4 address
     * @param port Remote endpoint's port
     * @param listener The registered listener for callbacks
     */
    class TCPConnection(
        ipAddress: String,
        port: Int,
        private val isOverAdb: Boolean,
        private val listener: Connection.Listener
    ) : Connection() {

        override val maxPacketSize = 65472

        private var socket: Socket
        private var outputStream: OutputStream? = null
        private var inputStream: InputStream? = null

        private var thread: Thread
        private val running = AtomicBoolean(true)

        init {
            try {
                socket = Socket(ipAddress, port)

                outputStream = socket.getOutputStream()
                inputStream = socket.getInputStream()

                Log.d(TAG, "TCP Connected to $ipAddress:$port")

                thread = Thread { startReceiveBytesLoop() }
                thread.start()
            } catch (e: Exception) {
                throw ConnectionFailedException("$ipAddress:$port")
            }
        }

        override fun send(bytes: ByteArray) {
            socket.getOutputStream().write(bytes)
        }

        override fun send(bytes: ByteArray, size: Int) {
            socket.getOutputStream().write(bytes, 0, size)
        }

        private fun startReceiveBytesLoop() {
            val buf = ByteArray(15)
            while (running.get()) {
                try {
                    val bytes = inputStream?.read(buf)
                    // When connected over ADB stream end of file might be reached
                    if(isOverAdb && bytes == -1) {
                        listener.onDisconnected()
                        break
                    }
                    listener.onBytesReceived(buf, bytes ?: 0)
                } catch (e: Exception) {
                    Log.e(TAG, "Error while reading. Closing socket. ${e.message}")
                    listener.onDisconnected()
                    break
                }
            }
        }

        override fun close() {
            running.set(false)
            socket.close()
            thread.join()
        }
    }

    private fun setConnectionStateCallback(connectionStateCallback: ConnectionStateCallback) {
        this.connectionStateCallback = connectionStateCallback
    }

    /**
     * The bytesReceivedListener is called only for packets of type different than PacketType.ACTIVATION.
     * These packets are handled internally
     */
    fun setOnBytesReceivedCallback(onBytesReceivedCallback: (buffer: ByteArray, bytes: Int) -> Unit) {
        this.onBytesReceivedCallback = onBytesReceivedCallback
    }

    /**
     * Connect in WIFI mode.
     * Tcp socket is used only for commands, video frames are streamed using Udp
     */
    fun connect(ipAddress: String, port: Int, deviceDetails: String) {
        connectionStateCallback?.onConnectionInitiated()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tcpConn = TCPConnection(ipAddress, port, false, this@ConnectionManager)
                udpConn = UDPConnection(ipAddress, port)
                tcpConn!!.send(deviceDetails.toByteArray())
                connectionStateCallback?.onConnectionSuccessful(Mode.WIFI)
            } catch (e: Connection.ConnectionFailedException) {
                Log.e(TAG, "Connection manager: ${e.message}")
                connectionStateCallback?.onConnectionFailed(Mode.WIFI)
            }
        }
    }

    /**
     * Connect in USB mode (through adb)
     * Tcp socket is used both for connection and streaming (adb doesn't allow Udp port forwarding)
     */
    fun connect(port: Int, deviceDetails: String) {
        connectionStateCallback?.onConnectionInitiated()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tcpConn = TCPConnection("127.0.0.1", port, true, this@ConnectionManager)
                tcpConn!!.send(deviceDetails.toByteArray())
                connectionStateCallback?.onConnectionSuccessful(Mode.USB)
            } catch (e: Connection.ConnectionFailedException) {
                Log.e(TAG, "Connection manager: ${e.message}")
                connectionStateCallback?.onConnectionFailed(Mode.USB)
            }
        }
    }

    fun sendBytes(bytes: ByteArray, withCoroutine: Boolean = false) {
        if(connection == null) {
            return;
        }

        when(withCoroutine) {
            false -> connection.send(bytes)
            true -> CoroutineScope(Dispatchers.IO).launch { connection.send(bytes) }
        }
    }

    override fun onBytesReceived(buffer: ByteArray, bytes: Int) {
        onBytesReceivedCallback?.let { it(buffer, bytes) }
    }

    override fun onDisconnected() {
        CoroutineScope(Dispatchers.Main).launch {
            streamingEnabled = false
            udpConn?.close()
            tcpConn?.close()
            connectionStateCallback?.onDisconnected()
        }
    }
}