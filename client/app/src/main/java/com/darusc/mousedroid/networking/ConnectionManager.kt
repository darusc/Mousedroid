package com.darusc.mousedroid.networking

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.darusc.mousedroid.BatteryMonitor
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.bluetooth.BluetoothConnection
import com.darusc.mousedroid.networking.sockets.TCPConnection
import com.darusc.mousedroid.networking.sockets.UDPConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnectionManager private constructor() : Connection.Listener, BatteryMonitor.Listener {

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
    
    init {
        BatteryMonitor.getInstance().addListener(this)
    }

    private var connectionStateCallback: ConnectionStateCallback? = null

    private var tcpConn: TCPConnection? = null
    private var udpConn: UDPConnection? = null
    private var btConn: BluetoothConnection? = null

    /**
     * Active connection. UDP is prioritized over TCP
     */
    private val connection: Connection?
        get() = udpConn ?: tcpConn ?: btConn

    private var connected = false

    interface ConnectionStateCallback {
        fun onConnectionInitiated(mode: Connection.Mode) {}
        fun onConnectionSuccessful(connectionMode: Connection.Mode, hostName: String) {}
        fun onConnectionFailed(connectionMode: Connection.Mode) {}
        fun onDisconnected(connectionMode: Connection.Mode, hostName: String) {}
    }

    private fun setConnectionStateCallback(connectionStateCallback: ConnectionStateCallback) {
        this.connectionStateCallback = connectionStateCallback
    }

    override fun onConnected(connectionMode: Connection.Mode, hostName: String) {
        connected = true
        connectionStateCallback?.onConnectionSuccessful(connectionMode, hostName)
    }

    override fun onConnectionFailed(connectionMode: Connection.Mode) {
        connectionStateCallback?.onConnectionFailed(connectionMode)
    }

    override fun onBytesReceived(buffer: ByteArray, bytes: Int) {}

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onDisconnected(connectionMode: Connection.Mode, hostName: String) {
        disconnect()
        connectionStateCallback?.onDisconnected(connectionMode, hostName)
    }

    override fun onBatteryPercentChanged(percentage: Int) {
        if (connected) {
            connection?.send(InputEvent.BatteryEvent(percentage))
        }
    }

    /**
     * Connect in WIFI mode.
     * Tcp socket is used only for connections, commands are sent using Udp
     */
    fun connectWIFI(ipAddress: String, port: Int, deviceDetails: String) {
        connectionStateCallback?.onConnectionInitiated(Connection.Mode.WIFI)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tcpConn = TCPConnection(ipAddress, port, false, this@ConnectionManager)
                udpConn = UDPConnection(ipAddress, port)
                tcpConn!!.sendBytes(deviceDetails.toByteArray())
                onConnected(Connection.Mode.WIFI, ipAddress)
            } catch (e: Connection.ConnectionFailedException) {
                Log.e(TAG, "Connection manager: ${e.message}")
                connectionStateCallback?.onConnectionFailed(Connection.Mode.WIFI)
            }
        }
    }

    /**
     * Connect in USB mode (through adb)
     * Tcp socket is used both for connection and sending commands (adb doesn't allow Udp port forwarding)
     */
    fun connectUSB(port: Int, deviceDetails: String) {
        connectionStateCallback?.onConnectionInitiated(Connection.Mode.USB)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tcpConn = TCPConnection("127.0.0.1", port, true, this@ConnectionManager)
                tcpConn!!.sendBytes(deviceDetails.toByteArray())
                onConnected(Connection.Mode.USB, "127.0.0.1")
            } catch (e: Connection.ConnectionFailedException) {
                Log.e(TAG, "Connection manager: ${e.message}")
                connectionStateCallback?.onConnectionFailed(Connection.Mode.USB)
            }
        }
    }

    /**
     * Register the bluetooth HID profile
     */
    @RequiresApi(Build.VERSION_CODES.P)
    fun registerBluetoothHID(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            btConn = BluetoothConnection(context, this@ConnectionManager)
        }
    }

    /**
     * Connect in bluetooth mode
     */
    @RequiresApi(Build.VERSION_CODES.P)
    fun connectBluetooth(macAddress: String) {
        connectionStateCallback?.onConnectionInitiated(Connection.Mode.BLUETOOTH)
        btConn?.connect(macAddress)
    }

    /**
     * Close active connection
     */
    @RequiresApi(Build.VERSION_CODES.P)
    fun disconnect() {
        CoroutineScope(Dispatchers.IO).launch {
            connected = false
            udpConn?.close()
            tcpConn?.close()
            btConn?.close()

            udpConn = null
            tcpConn = null
            btConn = null
        }
    }

    fun send(event: InputEvent, withCoroutine: Boolean = true) {
        if (connection == null) {
            return;
        }

        when (withCoroutine) {
            false -> connection?.send(event)
            true -> CoroutineScope(Dispatchers.IO).launch { connection?.send(event) }
        }
    }
}