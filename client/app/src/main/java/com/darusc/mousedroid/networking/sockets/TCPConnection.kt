package com.darusc.mousedroid.networking.sockets

import android.util.Log
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.Connection
import com.darusc.mousedroid.networking.toSocketReport
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

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
    private val listener: Listener
) : Connection() {

    private val TAG = "Mousedroid"

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

    override fun send(event: InputEvent) {
        val reportList = event.toSocketReport()
        reportList.forEach {
            socket.getOutputStream().write(it)
            // Send data immediately and add a small delay, otherwise all bytes will be merged
            // (fix for multiple keypress events for one character)
            socket.getOutputStream().flush()
            Thread.sleep(10)
        }
    }

    fun sendBytes(bytes: ByteArray) {
        socket.getOutputStream().write(bytes)
    }

    private fun startReceiveBytesLoop() {
        val buf = ByteArray(15)
        while (running.get()) {
            try {
                val bytes = inputStream?.read(buf)
                // When connected over ADB stream end of file might be reached
                if(isOverAdb && bytes == -1) {
                    onDisconnected()
                    break
                }
                listener.onBytesReceived(buf, bytes ?: 0)
            } catch (e: Exception) {
                Log.e(TAG, "Error while reading. Closing socket. ${e.message}")
                onDisconnected()
                break
            }
        }
    }

    override fun close() {
        running.set(false)
        socket.close()
        thread.join()
    }

    private fun onDisconnected() {
        val host = socket.inetAddress.hostAddress ?: "x.x.x.x"
        val mode = if (host == "127.0.0.1") Mode.USB else Mode.WIFI
        listener.onDisconnected(mode, host)
    }
}