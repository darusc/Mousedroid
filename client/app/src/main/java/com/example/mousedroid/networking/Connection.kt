package com.example.mousedroid.networking

abstract class Connection {

    protected val TAG = "Mousedroid"

    class PacketType {
        companion object {
            const val MOVE: Byte = 0x00
            const val LCLICK: Byte = 0x01
            const val RCLICK: Byte=  0x02
            const val KEYDOWN: Byte = 0x03
            const val KEYUP: Byte = 0x04
            const val NUMPAD: Byte = 0x05
        }
    }

    abstract val maxPacketSize: Int

    class ConnectionFailedException(host: String) : Exception("Connection to $host failed!")

    interface Listener {
        fun onBytesReceived(buffer: ByteArray, bytes: Int)
        fun onDisconnected()
    }

    abstract fun send(bytes: ByteArray)
    abstract fun send(bytes: ByteArray, size: Int)
    abstract fun close()
}