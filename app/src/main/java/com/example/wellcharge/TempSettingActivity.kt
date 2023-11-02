package com.example.wellcharge

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder
import com.example.wellcharge.service.SettingValue

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

//       ---------------Make Initial---------------------
        //      get iLed from object
        val iTempSetPoint = SettingValue.getITempSetPoint()
        val iMaxC = SettingValue.getIMaxCurrentCharging()
//        set Default iLed
        if (iTempSetPoint != null) {
            setDefaultTempInit(iTempSetPoint, true)
        }else{
            setDefaultTempInit(60, true)
        }
//       ---------------Make Initial---------------------
        when(iMaxC){
            16->tempSeeker.max = 20
            32->tempSeeker.max = 40
        }

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
                mService.mConnectedThread?.write("\"{\"cmd\":25,\"iTempSetPoint\":$temp}\"\n")
                SettingValue.setITempSetPoint(Integer.parseInt(temp))
            }
        })

        backBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDefaultTempInit(temp : Int, onInput : Boolean){
        val tempShow : TextView = findViewById(R.id.tempShow)
        val minValue = 60
        tempShow.text = "Temperature $temp °C"
        if(onInput){
        val tempSeeker : SeekBar = findViewById(R.id.TempSeeker)
            tempSeeker.progress = temp - minValue
        }

    }



}

