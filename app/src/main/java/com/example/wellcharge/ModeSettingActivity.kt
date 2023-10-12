package com.example.wellcharge

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder

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

        //set tick mode here
        des.text = ""

        setModeSetting("Auto")
        //********------------********

        modeAuto.setOnClickListener {
            setModeSetting("Auto")
        }
        modeAlarm.setOnClickListener {
            setModeSetting("Alarm")
        }
        modeStop.setOnClickListener {
            setModeSetting("Stop")
        }

        backBtn.setOnClickListener {
            finish()
        }


    }

    private fun setModeSetting(mode : String){
        val des : TextView = findViewById(R.id.DescriptionMode)
        val modeAuto : Button = findViewById(R.id.modeAuto)
        val modeAlarm : Button = findViewById(R.id.modeAlarm)
        val modeStop : Button = findViewById(R.id.modeStop)
        modeAuto.compoundDrawablePadding = 15
        modeAlarm.compoundDrawablePadding = 15
        modeStop.compoundDrawablePadding = 15

        if(mode == "Auto"){
            val image = R.drawable.asset_17
            modeAuto.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)
            modeAlarm.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            modeStop.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)

            des.text = getString(R.string.AutoModeDes)
            mService.mConnectedThread?.write("autoMode")
        }
        if(mode == "Alarm"){
            modeAuto.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            modeAlarm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)
            modeStop.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)

            des.text = getString(R.string.AlarmModeDes)
            mService.mConnectedThread?.write("AlarmMode")
        }
        if(mode == "Stop"){
            modeAuto.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            modeAlarm.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            modeStop.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)

            des.text = getString(R.string.StopModeDes)
            mService.mConnectedThread?.write("StopMode")
        }
    }
}
