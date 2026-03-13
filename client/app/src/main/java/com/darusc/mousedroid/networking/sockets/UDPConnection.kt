package com.darusc.mousedroid.networking.sockets

import android.util.Log
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.Connection
import com.darusc.mousedroid.networking.toSocketReport
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Wrapper around a socket to manage the TCP connection.
 * Using it only to send the video stream. No packet receiving implementation.
 */
class UDPConnection(
    ipAddress: String,
    private val port: Int,
) : Connection() {

    private val TAG = "Mousedroid"

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

    override fun send(event: InputEvent) {
        try {
            val reportList = event.toSocketReport()
            reportList.forEach {
                socket.send(DatagramPacket(it, it.size, address, port))
                // Delay similar to HID polling rate
                Thread.sleep(15)
            }
        } catch (_: Exception) {}
    }

    fun sendBytes(bytes: ByteArray) {
        try {
            socket.send(DatagramPacket(bytes, bytes.size, address, port))
            //socket.receive(DatagramPacket(ByteArray(5), 5, address, port))
        } catch (_: Exception) {}
    }

    override fun close() {
        socket.close()
    }
}