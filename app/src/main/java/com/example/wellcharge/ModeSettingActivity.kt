package com.example.wellcharge

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder
import com.example.wellcharge.service.SettingValue

class ModeSettingActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mService = ServiceHolder.getService()!!

        setContentView(R.layout.mode_setting)
        val modeAuto : Button = findViewById(R.id.modeAuto)
        val modeAlarm : Button = findViewById(R.id.modeAlarm)
        val modeStop : Button = findViewById(R.id.modeStop)
        val backBtn : Button = findViewById(R.id.backBtn)
        val des : TextView = findViewById(R.id.DescriptionMode)

        //set init value
        des.text = ""
        SettingValue.getIMode()
        when(SettingValue.getIMode()){
            0 -> setModeSetting(mode = "Auto",toDevice = false)
            1 -> setModeSetting(mode = "Alarm",toDevice = false)
            2 -> setModeSetting(mode = "Stop",toDevice = false)
        }
        //********------------********

        modeAuto.setOnClickListener {
            setModeSetting(mode = "Auto",toDevice = true)
        }
        modeAlarm.setOnClickListener {
            setModeSetting(mode = "Alarm",toDevice = true)
        }
        modeStop.setOnClickListener {
            setModeSetting(mode = "Stop",toDevice = true)
        }

        backBtn.setOnClickListener {
            finish()
        }


    }

    private fun setModeSetting(mode : String,toDevice : Boolean = true){
        val des : TextView = findViewById(R.id.DescriptionMode)
        val modeAuto : Button = findViewById(R.id.modeAuto)
        val modeAlarm : Button = findViewById(R.id.modeAlarm)
        val modeStop : Button = findViewById(R.id.modeStop)
        modeAuto.compoundDrawablePadding = 15
        modeAlarm.compoundDrawablePadding = 15
        modeStop.compoundDrawablePadding = 15
        var tick : Int = R.drawable.tick
        if(SettingValue.getIsTablet()){
            tick = R.drawable.tick_default
        }
        if(mode == "Auto"){
            modeAuto.setCompoundDrawablesWithIntrinsicBounds(tick,0,0,0)
            modeAlarm.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            modeStop.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)

            des.text = getString(R.string.AutoModeDes)
            if(toDevice) {
                SettingValue.setIMode(0)
                mService.mConnectedThread?.write("{\"cmd\":24,\"iMode\":0}\n")
            }
        }
        if(mode == "Alarm"){
            modeAuto.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            modeAlarm.setCompoundDrawablesWithIntrinsicBounds(tick,0,0,0)
            modeStop.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)

            des.text = getString(R.string.AlarmModeDes)
            if(toDevice) {
                SettingValue.setIMode(1)
                mService.mConnectedThread?.write("{\"cmd\":24,\"iMode\":1}\n")
            }
        }
        if(mode == "Stop"){
            modeAuto.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            modeAlarm.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            modeStop.setCompoundDrawablesWithIntrinsicBounds(tick,0,0,0)

            des.text = getString(R.string.StopModeDes)
            if(toDevice) {
                SettingValue.setIMode(2)
                mService.mConnectedThread?.write("{\"cmd\":24,\"iMode\":2}\n")
            }
        }
    }

}


