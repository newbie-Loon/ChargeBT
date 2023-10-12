package com.example.wellcharge

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder

class LedSettingActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mService = ServiceHolder.getService()!!

        setContentView(R.layout.led_light_setting)

        val led1 : Button = findViewById(R.id.ledMode1)
        val led2 : Button = findViewById(R.id.ledMode2)
        val led3 : Button = findViewById(R.id.ledMode3)
        val backBtn : Button = findViewById(R.id.backBtn)

        setModeSetting("led1")

        led1.setOnClickListener {
            setModeSetting("led1")
        }
        led2.setOnClickListener {
            setModeSetting("led2")
        }
        led3.setOnClickListener {
            setModeSetting("led3")
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setModeSetting(mode : String){
        val led1 : Button = findViewById(R.id.ledMode1)
        val led2 : Button = findViewById(R.id.ledMode2)
        val led3 : Button = findViewById(R.id.ledMode3)
        led1.compoundDrawablePadding = 15
        led2.compoundDrawablePadding = 15
        led3.compoundDrawablePadding = 15

        if(mode == "led1"){
            led1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)
            led2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            led3.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            mService.mConnectedThread?.write("led1")
        }
        if(mode == "led2"){
            led1.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            led2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)
            led3.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            mService.mConnectedThread?.write("led2")
        }
        if(mode == "led3"){
            led1.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            led2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            led3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)
            mService.mConnectedThread?.write("led3")
        }
    }
}
