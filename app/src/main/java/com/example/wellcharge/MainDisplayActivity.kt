package com.example.wellcharge

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder
import com.example.wellcharge.service.SettingValue
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InvalidClassException
import java.io.NotSerializableException

class MainDisplayActivity : ComponentActivity() {

    private lateinit var mService: BTKeepConnService
    private var initChipId = true
    private var initChargingStatus = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_display)
        val setI: ImageButton = findViewById(R.id.setI)
        val setT: ImageButton = findViewById(R.id.setT)
        val setEtc: ImageButton = findViewById(R.id.setEtc)
        val setStop: ImageButton = findViewById(R.id.setStop)
        val setStart : ImageButton = findViewById(R.id.setStart)
        val someDeviceName = intent?.getStringExtra("someDeviceName")
        val someDeviceAddr = intent?.getStringExtra("someDeviceAddr")

        val serviceConnStat = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName, service: IBinder) {
                val binder = service as BTKeepConnService.LocalBinder
                mService = binder.getService()
//                mBound = true
                ServiceHolder.setService(mService)
            }

            override fun onServiceDisconnected(className: ComponentName) {
//                mBound = false
            }
        }

//        mService = ServiceHolder.getService()!!
        val intentStartBTService = Intent(this, BTKeepConnService::class.java)
        intentStartBTService.putExtra("someDeviceName", someDeviceName)
        intentStartBTService.putExtra("someDeviceAddr", someDeviceAddr)
        bindService(intentStartBTService, serviceConnStat, Context.BIND_AUTO_CREATE)
        Thread.sleep(2000)
        setReadThread()

        setI.setOnClickListener {
            val intent = Intent(applicationContext, SetCurrentActivity::class.java)
            startActivity(intent);
        }
        setT.setOnClickListener {
            val intent = Intent(applicationContext, SetTimePickerActivity::class.java)
            startActivity(intent);
        }
        setEtc.setOnClickListener {
            val intent = Intent(applicationContext, EtcSettingActivity::class.java)
            startActivity(intent);
        }
        setStop.setOnClickListener {
            mService.mConnectedThread?.write("{\"cmd\":30,\"bAutoCharging\":false}\n")
        }
        setStart.setOnClickListener{
            mService.mConnectedThread?.write("{\"cmd\":30,\"bAutoCharging\":true}\n")
        }
    }

    private fun setReadThread() {
        val printThread = Thread {
            while (true) {
                getData()
                Thread.sleep(1000)
            }
        }
        printThread.start()
    }


    private fun getData() {
        val directory: File? = this.getExternalFilesDir(null)
        val subdirectory: File = File(directory, "/dataLogger")
        val jsonArray = JSONArray()

        val isDirectoryExists = if (!subdirectory.exists()) {
            subdirectory.mkdirs()
        } else true
        if (isDirectoryExists) {
            val destination: File = File(subdirectory, "report.txt")
            try {
                val fin = FileInputStream(destination).bufferedReader()
                if (fin.ready()) {
                    while (fin.ready()) {
                        val line: String = fin.readLine()
                        val jsonObject = JSONObject(line)
                        if(jsonObject.has("strChipId")){
                            runOnUiThread{
                                if(initChipId){
                                    SettingValue.setSetting(jsonObject)
                                    showToast("ChipId: "+ jsonObject.getString("strChipId"))
                                    initChipId = false
                                }
                            }
//
                        }else{
                            jsonArray.put(jsonObject)
                        }
                    }
                    fin.close()
                runOnUiThread {
                        getlastValue(jsonArray)
                }
                }
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

    private fun getlastValue(dataLogger: JSONArray) {
        val voltage: TextView = findViewById(R.id.voltage)
        val current: TextView = findViewById(R.id.current)
        val plugTemp: TextView = findViewById(R.id.plugTemp)
        val relayTemp: TextView = findViewById(R.id.relayTemp)
        val status :TextView = findViewById(R.id.status)
        val line = dataLogger.length() - 1
        if(dataLogger.getJSONObject(line).has("fVoltage")){
            voltage.text = dataLogger.getJSONObject(line).getDouble("fVoltage").toString()
        }
        if(dataLogger.getJSONObject(line).has("fCurrent")){
            current.text = dataLogger.getJSONObject(line).getDouble("fCurrent").toString()
        }
        if(dataLogger.getJSONObject(line).has("fTempPlug")){
            plugTemp.text = dataLogger.getJSONObject(line).getDouble("fTempPlug").toString()
        }
        if(dataLogger.getJSONObject(line).has("fTempRelay")){
            relayTemp.text = dataLogger.getJSONObject(line).getDouble("fTempRelay").toString()
        }
        if(dataLogger.getJSONObject(line).has("iChargingState")){
            when(dataLogger.getJSONObject(line).getInt("iChargingState")){
                -1 -> {
                    status.text = "Error"
                }
                0 -> {
                    status.text = getString(R.string.notConnect)
                }
                1 -> {
                    status.text = getString(R.string.vehicleConnected)
                }
                2 ->{
                    status.text = getString(R.string.charging)
                }
                3->{
                    status.text = getString(R.string.fullCharge)
                }
                10->{
                    status.text = getString(R.string.stopCharging)
                }
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
