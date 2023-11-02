package com.example.wellcharge.service

import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InvalidClassException
import java.io.NotSerializableException
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID


class BTKeepConnService() : Service() {

    private val handlerState = 0
    private var bluetoothIn: Handler? = null
    private var btAdapter: BluetoothAdapter? = null
    var mConnectingThread: ConnectingThread? = null
    var mConnectedThread: ConnectedThread? = null
    private var stopThread = false

    //    var device = null
    private val context = this
    private val recDataString = StringBuilder()
    private val mBinder = LocalBinder()

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")


    override fun onCreate() {
        super.onCreate()
        stopThread = false

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bluetoothIn = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: android.os.Message) {
                if (msg.what == handlerState) {
                    val readMessage = msg.obj as String
                    recDataString.append(readMessage)
                    // Do stuff here with your data, like adding it to the database
                }
                recDataString.delete(0, recDataString.length)
            }
        }

        Toast.makeText(this, "message onBind", Toast.LENGTH_SHORT).show()


        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val someDeviceName = intent?.getStringExtra("someDeviceName")
        val someDeviceAddr = intent?.getStringExtra("someDeviceAddr")
        btAdapter = bluetoothManager.adapter
        val pairedDevices: MutableSet<BluetoothDevice>? = btAdapter?.bondedDevices
//        var deviceSelected : BluetoothDevice? = null
        if (pairedDevices != null) {
            for (device in pairedDevices) {
                if (device.name == someDeviceName) {
//                    deviceSelected = device
                    MAC_ADDRESS = device.address
                    checkBTState(device)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothIn?.removeCallbacksAndMessages(null)
        stopThread = true
        mConnectedThread?.closeStreams()
        mConnectingThread?.closeSocket()
    }

    @SuppressLint("MissingPermission")
    override fun onBind(intent: Intent?): IBinder? {
        bluetoothIn = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: android.os.Message) {
                if (msg.what == handlerState) {
                    val readMessage = msg.obj as String
                    recDataString.append(readMessage)
                    // Do stuff here with your data, like adding it to the database
                }
                recDataString.delete(0, recDataString.length)
            }
        }

        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val someDeviceName = intent?.getStringExtra("someDeviceName")
        btAdapter = bluetoothManager.adapter
        val pairedDevices: MutableSet<BluetoothDevice>? = btAdapter?.bondedDevices
        if (pairedDevices != null) {
            for (device in pairedDevices) {
                if (device.name == someDeviceName) {
//                    deviceSelected = device
                    MAC_ADDRESS = device.address
                    checkBTState(device)
                }
            }
        }
        return mBinder
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    private fun checkBTState(device: BluetoothDevice) {
        if (btAdapter == null) {
            stopSelf()
        } else {
            if (btAdapter!!.isEnabled) {
                try {
                    mConnectingThread = ConnectingThread(device)
                    mConnectingThread!!.start()
                } catch (e: IllegalArgumentException) {

                    stopSelf()
                }
            } else {
                stopSelf()
            }
        }
    }

    @SuppressLint("MissingPermission")
    inner class ConnectingThread(device: BluetoothDevice) : Thread() {
        private val mmSocket: BluetoothSocket
        private val mmDevice: BluetoothDevice

        init {
            Log.d("DEBUG BT", "IN CONNECTING THREAD")
            mmDevice = device
            var temp: BluetoothSocket? = null
            Log.d("DEBUG BT", "MAC ADDRESS : $MAC_ADDRESS")
            Log.d("DEBUG BT", "BT UUID : $BTMODULEUUID")
            try {
                temp = mmDevice.createRfcommSocketToServiceRecord(BTMODULEUUID)
                Log.d("DEBUG BT", "SOCKET CREATED : $temp")
            } catch (e: IOException) {
                Log.d("DEBUG BT", "SOCKET CREATION FAILED :${e.toString()}")
                Log.d("BT SERVICE", "SOCKET CREATION FAILED, STOPPING SERVICE")
                stopSelf()
            }
            mmSocket = temp!!
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("MissingPermission")
        override fun run() {
            super.run()
            try {
                mmSocket.connect()
                mConnectedThread = ConnectedThread(mmSocket)
                mConnectedThread!!.start()
                val dateTimeNow = LocalDateTime.now().format(dateTimeFormatter)

                mConnectedThread!!.write("{\"cmd\":21,\"Datetime\":\"$dateTimeNow\"}\n")
                mConnectedThread!!.write("{\"cmd\":10}")

            } catch (e: IOException) {
                try {
                    mmSocket.close()
                    stopSelf()
                } catch (e2: IOException) {
                    stopSelf()
                }
            } catch (e: IllegalStateException) {
                stopSelf()
            }
        }

        fun closeSocket() {
            try {
                mmSocket.close()
            } catch (e2: IOException) {
                stopSelf()
            }
        }
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): BTKeepConnService = this@BTKeepConnService

    }

    inner class ConnectedThread(socket: BluetoothSocket) : Thread() {
        private val mmInStream: InputStream
        private val mmOutStream: OutputStream

        init {
            var tmpIn: InputStream? = null
            var tmpOut: OutputStream? = null
            try {
                tmpIn = socket.inputStream
                tmpOut = socket.outputStream
            } catch (e: IOException) {
                stopSelf()
            }
            mmInStream = tmpIn!!
            mmOutStream = tmpOut!!
        }

        override fun run() {
            while (!stopThread) {
                try {
                    val buffer = ByteArray(mmInStream.available())
                    var bytesRead = mmInStream.read(buffer)
                    writeLoggerData(buffer, 0, bytesRead)
                    val readMessage = String(buffer, 0, bytesRead)
                    bluetoothIn?.obtainMessage(handlerState, bytesRead, -1, readMessage)?.sendToTarget()
                } catch (e: IOException) {
                    stopSelf()
                    break
                }
            }
        }

        private fun writeLoggerData(data: ByteArray, offset: Int, length: Int) {
            val directory: File? = context.getExternalFilesDir(null)
            val subdirectory: File = File(directory, "/dataLogger")
            val isDirectoryExists = if (!subdirectory.exists()) {
                subdirectory.mkdirs()

            } else true
            if (isDirectoryExists) {
                val destination: File = File(subdirectory, "report.txt")
                try {
                    val toLoggerFile = FileOutputStream(destination, true)
                    toLoggerFile.write(data, offset, length)
                    toLoggerFile.close()

                } catch (e: NullPointerException) {
                    // Handle an error
                } catch (e: FileNotFoundException) {
                    // Handle an error
                } catch (e: SecurityException) {
                    // Handle an error
                } catch (e: IOException) {
                    // Handle an error
                } catch (e: InvalidClassException) {
                    // Handle an error
                } catch (e: NotSerializableException) {
                    // Handle an error
                }
            }
        }

        @SuppressLint("SuspiciousIndentation")
        fun write(input: String) {
            try {
                val msgBuffer = input.toByteArray()
                mmOutStream.write(msgBuffer)
            } catch (e: IOException) {
                stopSelf()
            }
        }

        fun closeStreams() {
            try {
                mmInStream.close()
                mmOutStream.close()
            } catch (e2: IOException) {
                stopSelf()
            }
        }
    }

    companion object {
        private val BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        private var MAC_ADDRESS : String? = null
    }
}