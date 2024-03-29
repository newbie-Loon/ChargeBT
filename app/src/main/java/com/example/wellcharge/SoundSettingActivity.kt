package com.example.wellcharge

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder
import com.example.wellcharge.service.SettingValue

class SoundSettingActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mService = ServiceHolder.getService()!!

        setContentView(R.layout.sound_mode_setting)
        val soundOff : Button = findViewById(R.id.soundOff)
        val sound1 : Button = findViewById(R.id.sound1)
        val sound2 : Button = findViewById(R.id.sound2)
        val backBtn : Button = findViewById(R.id.backBtn)

//      get iLed from object
        val iBuzzer = SettingValue.getIBuzzer()

        when(iBuzzer){
            1 -> setModeSetting(mode = "sound1",toDevice = false)
            2 ->  setModeSetting(mode = "sound2",toDevice = false)
            else -> {
                setModeSetting(mode = "soundOff",toDevice = false)
            }
        }

        soundOff.setOnClickListener{
            setModeSetting("soundOff")
        }
        sound1.setOnClickListener{
            setModeSetting("sound1")
        }
        sound2.setOnClickListener{
            setModeSetting("sound2")
        }

        backBtn.setOnClickListener{
            finish()
        }

    }

    private fun setModeSetting(mode : String , toDevice : Boolean = true){
        val soundOff : Button = findViewById(R.id.soundOff)
        val sound1 : Button = findViewById(R.id.sound1)
        val sound2 : Button = findViewById(R.id.sound2)

        sound1.compoundDrawablePadding = 15
        sound2.compoundDrawablePadding = 15
        soundOff.compoundDrawablePadding = 15
        var tick : Int = R.drawable.tick
        if(SettingValue.getIsTablet()){
            tick = R.drawable.tick_default
        }
        if(mode == "soundOff"){
            soundOff.setCompoundDrawablesWithIntrinsicBounds(tick,0,0,0)
            sound1.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            sound2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            if(toDevice) {
                SettingValue.setIBuzzer(0)
                mService.mConnectedThread?.write("{\"cmd\":26,\"iBuzzer\":0}\n")
            }
        }
        if(mode == "sound1"){
            soundOff.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            sound1.setCompoundDrawablesWithIntrinsicBounds(tick,0,0,0)
            sound2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            if(toDevice) {
                SettingValue.setIBuzzer(1)
                mService.mConnectedThread?.write("{\"cmd\":26,\"iBuzzer\":1}\n")
            }
        }
        if(mode == "sound2"){
            soundOff.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            sound1.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            sound2.setCompoundDrawablesWithIntrinsicBounds(tick,0,0,0)
            if(toDevice) {
                SettingValue.setIBuzzer(2)
                mService.mConnectedThread?.write("{\"cmd\":26,\"iBuzzer\":2}\n")
            }
        }
    }
}
