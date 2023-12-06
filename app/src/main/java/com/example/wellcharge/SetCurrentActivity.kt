package com.example.wellcharge

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder
import com.example.wellcharge.service.SettingValue

class SetCurrentActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_voltage)

        mService = ServiceHolder.getService()!!

        val a6 : ImageButton = findViewById(R.id.a6)
        val a13 : ImageButton = findViewById(R.id.a13)
        val a10 : ImageButton = findViewById(R.id.a10)
        val a16 : ImageButton = findViewById(R.id.a16)
        val a20 : ImageButton = findViewById(R.id.a20)
        val a25 : ImageButton = findViewById(R.id.a25)
        val a32 : ImageButton = findViewById(R.id.a32)

        when(SettingValue.getIMaxCurrentCharging()){
            16 -> {
                a20.visibility = View.INVISIBLE
                a25.visibility = View.INVISIBLE
                a32.visibility = View.INVISIBLE
            }
            32 ->{
                a20.visibility = View.VISIBLE
                a25.visibility = View.VISIBLE
                a32.visibility = View.VISIBLE
            }
        }
        if(SettingValue.getIChargingCurrent() != null){

            showToast("ChargingCurrent : " + SettingValue.getIChargingCurrent())
        }

        a6.setOnClickListener {
            SettingValue.setIChargingCurrent(6)
            mService.mConnectedThread?.write("{\"cmd\":22,\"iChargingCurrent\":8}\n")
//            showToast("")
            finish()
        }
        a10.setOnClickListener {
            SettingValue.setIChargingCurrent(10)
            mService.mConnectedThread?.write("{\"cmd\":22,\"iChargingCurrent\":10}\n")
//            showToast("")
            finish()
        }
        a13.setOnClickListener {
            SettingValue.setIChargingCurrent(13)
            mService.mConnectedThread?.write("{\"cmd\":22,\"iChargingCurrent\":13}\n")
//            showToast("")
            finish()
        }
        a16.setOnClickListener {
            SettingValue.setIChargingCurrent(16)
            mService.mConnectedThread?.write("{\"cmd\":22,\"iChargingCurrent\":16}\n")
//            showToast("")
            finish()
        }
        a20.setOnClickListener {
            SettingValue.setIChargingCurrent(20)
            mService.mConnectedThread?.write("{\"cmd\":22,\"iChargingCurrent\":20}\n")
//            showToast("")
            finish()
        }
        a25.setOnClickListener {
            SettingValue.setIChargingCurrent(25)
            mService.mConnectedThread?.write("{\"cmd\":22,\"iChargingCurrent\":25}\n")
//            showToast("")
            finish()
        }
        a32.setOnClickListener {
            SettingValue.setIChargingCurrent(32)
            mService.mConnectedThread?.write("{\"cmd\":22,\"iChargingCurrent\":32}\n")
//            showToast("")
            finish()
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
