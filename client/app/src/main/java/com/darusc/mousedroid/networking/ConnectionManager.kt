package com.darusc.mousedroid.networking

import android.util.Log
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.bluetooth.BluetoothConnection
import com.darusc.mousedroid.networking.sockets.TCPConnection
import com.darusc.mousedroid.networking.sockets.UDPConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnectionManager private constructor() : Connection.Listener {

    private val TAG = "Mousedroid"

    companion object {
        @Volatile
        private var instance: ConnectionManager? = null

        fun getInstance(): ConnectionManager {
            synchronized(this) {
                return instance ?: ConnectionManager().also { instance = it }
            }
        }

        fun getInstance(connectionStateCallback: ConnectionStateCallback): ConnectionManager {
            synchronized(this) {
                if (instance == null) {
                    instance = ConnectionManager()
                }
                instance!!.setConnectionStateCallback(connectionStateCallback)
                return instance!!
            }
        }
    }

    enum class Mode {
        USB,
        WIFI,
        BLUETOOTH
    }

    private var connectionStateCallback: ConnectionStateCallback? = null

    private var tcpConn: TCPConnection? = null
    private var udpConn: UDPConnection? = null
    private var btConn: BluetoothConnection? = null

    /**
     * Active connection. Prioritize UDP connection if available
     * otherwise fall back to the TCP Conneciton
     */
    private val connection
        get() = (udpConn ?: tcpConn) as Connection

    private var streamingEnabled = false

    interface ConnectionStateCallback {
        fun onConnectionInitiated() {}
        fun onConnectionSuccessful(connectionMode: Mode) {}
        fun onConnectionFailed(connectionMode: Mode) {}
        fun onDisconnected() {}
    }

    private fun setConnectionStateCallback(connectionStateCallback: ConnectionStateCallback) {
        this.connectionStateCallback = connectionStateCallback
    }

    /**
     * Connect in WIFI mode.
     * Tcp socket is used only for connections, commands are sent using Udp
     */
    fun connectWIFI(ipAddress: String, port: Int, deviceDetails: String) {
        connectionStateCallback?.onConnectionInitiated()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tcpConn = TCPConnection(ipAddress, port, false, this@ConnectionManager)
                udpConn = UDPConnection(ipAddress, port)
                tcpConn!!.sendBytes(deviceDetails.toByteArray())
                connectionStateCallback?.onConnectionSuccessful(Mode.WIFI)
            } catch (e: Connection.ConnectionFailedException) {
                Log.e(TAG, "Connection manager: ${e.message}")
                connectionStateCallback?.onConnectionFailed(Mode.WIFI)
            }
        }
    }

    /**
     * Connect in USB mode (through adb)
     * Tcp socket is used both for connection and sending commands (adb doesn't allow Udp port forwarding)
     */
    fun connectUSB(port: Int, deviceDetails: String) {
        connectionStateCallback?.onConnectionInitiated()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tcpConn = TCPConnection("127.0.0.1", port, true, this@ConnectionManager)
                tcpConn!!.sendBytes(deviceDetails.toByteArray())
                connectionStateCallback?.onConnectionSuccessful(Mode.USB)
            } catch (e: Connection.ConnectionFailedException) {
                Log.e(TAG, "Connection manager: ${e.message}")
                connectionStateCallback?.onConnectionFailed(Mode.USB)
            }
        }
    }

    /**
     * Connect in bluetooth mode
     */
    fun connectBluetooth() {

    }

    fun disconnect() {
        CoroutineScope(Dispatchers.IO).launch {
            tcpConn?.close()
            udpConn?.close()
        }
    }

    fun send(event: InputEvent, withCoroutine: Boolean) {
        if (connection == null) {
            return;
        }

        when (withCoroutine) {
            false -> connection.send(event)
            true -> CoroutineScope(Dispatchers.IO).launch { connection.send(event) }
        }
    }

    override fun onBytesReceived(buffer: ByteArray, bytes: Int) {}

    override fun onDisconnected() {
        CoroutineScope(Dispatchers.Main).launch {
            streamingEnabled = false
            udpConn?.close()
            tcpConn?.close()
            connectionStateCallback?.onDisconnected()
        }
    }
}