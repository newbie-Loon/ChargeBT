package com.example.wellcharge

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder

class TempSettingActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp_setting)
        val tempShow : TextView = findViewById(R.id.tempShow)
        val tempSeeker : SeekBar = findViewById(R.id.TempSeeker)
        val backBtn : Button = findViewById(R.id.backBtn)
        mService = ServiceHolder.getService()!!
        var temp : String? = null

//       ---------------Make Initial
        setDefaultTempInit(60, true)

        tempSeeker.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            var minValue = 60
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // here, you react to the value being set in seekBar
                val change = progress + minValue
//                tempShow.text = "Temperature $change °C"
                setDefaultTempInit(change, false)
                temp = change.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
                mService.mConnectedThread?.write("$temp")

            }
        })

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setDefaultTempInit(temp : Int, onInput : Boolean){
        val tempShow : TextView = findViewById(R.id.tempShow)

        tempShow.text = "Temperature $temp °C"
        if(onInput){
        val tempSeeker : SeekBar = findViewById(R.id.TempSeeker)
            tempSeeker.progress = temp
        }

    }



}

