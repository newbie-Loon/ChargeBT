package com.example.wellcharge

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder
import com.example.wellcharge.service.SettingValue

class LedSettingActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mService = ServiceHolder.getService()!!

        setContentView(R.layout.led_light_setting)

        val ledOff: Button = findViewById(R.id.ledModeOff)
        val led1: Button = findViewById(R.id.ledMode1)
        val led2: Button = findViewById(R.id.ledMode2)
        val backBtn: Button = findViewById(R.id.backBtn)

//      get iLed from object
        val iLed = SettingValue.getILed()

        when(iLed){
            1 -> setModeSetting(mode = "led1", toDevice = false)
            2 -> setModeSetting(mode = "led2", toDevice = false)
            else -> {
                setModeSetting(mode = "ledOff", toDevice = false)
            }
        }


//        setModeSetting("led1")

        ledOff.setOnClickListener {
            setModeSetting(mode = "ledOff", toDevice = true)
        }
        led1.setOnClickListener {
            setModeSetting(mode = "led1", toDevice = true)
        }
        led2.setOnClickListener {
            setModeSetting(mode = "led2", toDevice = true)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
    private fun setModeSetting(mode: String, toDevice: Boolean = true) {
        val ledOff: Button = findViewById(R.id.ledModeOff)
        val led1: Button = findViewById(R.id.ledMode1)
        val led2: Button = findViewById(R.id.ledMode2)
        led1.compoundDrawablePadding = 15
        led2.compoundDrawablePadding = 15
        ledOff.compoundDrawablePadding = 15
        var tick: Int = R.drawable.tick
        if(SettingValue.getIsTablet()){
            tick = R.drawable.tick_default
        }
        if(mode == "ledOff"){
            ledOff.setCompoundDrawablesWithIntrinsicBounds(tick, 0, 0, 0)
            led1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            led2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            if(toDevice){
                SettingValue.setILed(0)
                mService.mConnectedThread?.write("{\"cmd\":27,\"iLed\":0}\n")
            }
        }
        if(mode == "led1"){
            ledOff.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            led1.setCompoundDrawablesWithIntrinsicBounds(tick, 0, 0, 0)
            led2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            if(toDevice){
                SettingValue.setILed(1)
                mService.mConnectedThread?.write("{\"cmd\":27,\"iLed\":1}\n")
            }
        }
        if(mode == "led2"){
            ledOff.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            led1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            led2.setCompoundDrawablesWithIntrinsicBounds(tick, 0, 0, 0)
            if(toDevice){
                SettingValue.setILed(2)
                mService.mConnectedThread?.write("{\"cmd\":27,\"iLed\":2}\n")
            }
        }
    }
}
