package com.darusc.mousedroid.networking

abstract class Connection {

    protected val TAG = "Mousedroid"

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