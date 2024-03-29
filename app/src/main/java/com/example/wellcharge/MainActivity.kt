package com.example.wellcharge

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.SettingValue
import java.io.File


class MainActivity : ComponentActivity() {
    companion object {
        private const val REQUEST_ENABLE_BT = 1
    }

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var mService: BTKeepConnService
    private var mBound: Boolean = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conn_bt)
        val btDevice: Spinner = findViewById(R.id.btSelected)
        val btConn: ImageButton = findViewById(R.id.btConn)
//        val wifiConn: ImageButton = findViewById(R.id.wifiConn)
        clearCatchData()
        getDeviceInfo(applicationContext)
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.BLUETOOTH_CONNECT
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestBluetoothPermissions()
        }
        if (!bluetoothAdapter.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT)
        }

        val pairedDevices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
        var deviceItemls = ArrayList<String>()
        val deviceBtList = ArrayList<BluetoothDevice>()
        deviceItemls = ArrayList<String>()
        deviceItemls.add("Select Device")
        for (device in pairedDevices) {
            deviceBtList.add(device)
            deviceItemls.add(device.name)

        }
        val adapterDevice = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line, deviceItemls
        )
        btDevice.adapter = adapterDevice


        val spinnerOnTouch = OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                //Your code
                val bluetoothManagerOnTouch: BluetoothManager = getSystemService(BluetoothManager::class.java)
                bluetoothAdapter = bluetoothManagerOnTouch.adapter

                if (!bluetoothAdapter.isEnabled) {
                    val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT)
                }
                val pairedDevicesin: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
                deviceItemls = ArrayList<String>()
                deviceItemls.add("Select Device")
                for (device in pairedDevicesin) {
                    deviceBtList.add(device)
                    deviceItemls.add(device.name)

                }
                val adapterDevicea = ArrayAdapter(
                    this,
                    android.R.layout.simple_dropdown_item_1line, deviceItemls
                )
                btDevice.adapter = adapterDevicea
            }
            false
        }
        val spinnerOnKey =
            View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    //your code
                    val bluetoothManagerOnkey: BluetoothManager = getSystemService(BluetoothManager::class.java)
                    bluetoothAdapter = bluetoothManagerOnkey.adapter

                    if (!bluetoothAdapter.isEnabled) {
                        val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT)
                    }
                    val pairedDevicesin: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
                    deviceItemls = ArrayList<String>()
                    deviceItemls.add("Select Device")
                    for (device in pairedDevicesin) {
                        deviceBtList.add(device)
                        deviceItemls.add(device.name)

                    }
                    val adapterDeviceb = ArrayAdapter(
                        this,
                        android.R.layout.simple_dropdown_item_1line, deviceItemls
                    )
                    btDevice.adapter = adapterDeviceb
                    true
                } else {
                    false
                }
            }

        btDevice.setOnTouchListener(spinnerOnTouch);
        btDevice.setOnKeyListener(spinnerOnKey);

        var someDeviceName: String? = null
        var someDeviceAddr: String? = null
        btDevice.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            @SuppressLint("MissingPermission")
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (deviceItemls[position] == "Select Device") {
                    someDeviceName = null
                    someDeviceAddr = null
                } else {
//                    someDeviceName = deviceItemls[position]
                    someDeviceName = deviceBtList[position - 1].name
                    someDeviceAddr = deviceBtList[position - 1].address
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        btConn.setOnClickListener {
            if (someDeviceName != null) {
//                val intentStartBTService = Intent(this, BTKeepConnService::class.java)
//                intentStartBTService.putExtra("someDeviceName", someDeviceName)
//                intentStartBTService.putExtra("someDeviceAddr", someDeviceAddr)
//                bindService(intentStartBTService, serviceConnStat, Context.BIND_AUTO_CREATE)
                val intent = Intent(applicationContext, MainDisplayActivity::class.java)
                intent.putExtra("someDeviceName", someDeviceName)
                intent.putExtra("someDeviceAddr", someDeviceAddr)
                startActivity(intent)
                finish()
            }
        }


    }

    private fun clearCatchData() {
        val directory: File? = this.getExternalFilesDir(null)
        val subdirectory: File = File(directory, "/dataLogger")
        val isDirectoryExists = if (!subdirectory.exists()) {
            subdirectory.mkdirs()

        } else true
        if (isDirectoryExists) {
            val destination: File = File(subdirectory, "report.txt")
            if (destination.exists()) {
                destination.delete()
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun requestBluetoothPermissions() {
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT
        )

        ActivityCompat.requestPermissions(this, permissions, REQUEST_ENABLE_BT)
    }
    private fun getDeviceInfo(context: Context?){
        try {
                return if (isTablet(context!!)) {
                    if (getDevice5Inch(context)) {
                        SettingValue.setIsTablet(true)
                    } else {
                        SettingValue.setIsTablet(false)
                    }
                } else {
                    SettingValue.setIsTablet(false)
                }

        } catch (e: java.lang.Exception) {
            SettingValue.setIsTablet(false)
            e.printStackTrace()
        }
    }


    private fun getDevice5Inch(context: Context): Boolean {
         try {
            val displayMetrics = context.resources.displayMetrics
            val yinch = displayMetrics.heightPixels / displayMetrics.ydpi
            val xinch = displayMetrics.widthPixels / displayMetrics.xdpi
            val diagonalinch = Math.sqrt((xinch * xinch + yinch * yinch).toDouble())
            if (diagonalinch >= 7) {
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
             return false
        }
    }
    private fun isTablet(context: Context): Boolean {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}