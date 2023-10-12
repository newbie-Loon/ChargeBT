package com.example.wellcharge

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder

class SoundSettingActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mService = ServiceHolder.getService()!!

        setContentView(R.layout.sound_mode_setting)
        val sound1 : Button = findViewById(R.id.sound1)
        val sound2 : Button = findViewById(R.id.sound2)
        val sound3 : Button = findViewById(R.id.sound3)
        val backBtn : Button = findViewById(R.id.backBtn)

        setModeSetting("sound1")

        sound1.setOnClickListener{
            setModeSetting("sound1")
        }
        sound2.setOnClickListener{
            setModeSetting("sound2")
        }
        sound3.setOnClickListener{
            setModeSetting("sound3")
        }

        backBtn.setOnClickListener{
            finish()
        }

    }

    private fun setModeSetting(mode : String){
        val sound1 : Button = findViewById(R.id.sound1)
        val sound2 : Button = findViewById(R.id.sound2)
        val sound3 : Button = findViewById(R.id.sound3)

        sound1.compoundDrawablePadding = 15
        sound2.compoundDrawablePadding = 15
        sound3.compoundDrawablePadding = 15
        if(mode == "sound1"){
            sound1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)
            sound2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            sound3.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            mService.mConnectedThread?.write("sound1")
        }
        if(mode == "sound2"){
            sound1.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            sound2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)
            sound3.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            mService.mConnectedThread?.write("sound2")
        }
        if(mode == "sound3"){
            sound1.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            sound2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            sound3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick__1_,0,0,0)
            mService.mConnectedThread?.write("sound3")
        }
    }
}
