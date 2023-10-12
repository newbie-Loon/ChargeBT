package com.example.wellcharge

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder

class SleepModeActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sleep_mode_setting)
        mService = ServiceHolder.getService()!!

        val closeWhenfinish : Switch = findViewById(R.id.closeWhenfinish)
        val chargeWhenPlug : Switch = findViewById(R.id.chargeWhenPlug)
        val backBtn : Button = findViewById(R.id.backBtn)



//        closeWhenfinish.isChecked = true
//        chargeWhenPlug.isChecked = true

        setInitValue(true,false)

        closeWhenfinish.setOnCheckedChangeListener { buttonView, isChecked ->
            // show timer picker if false
            if(isChecked){
                mService.mConnectedThread?.write("closeWhenfinish on")
            }else{
                mService.mConnectedThread?.write("closeWhenfinish off")
            }
        }
        chargeWhenPlug.setOnCheckedChangeListener { buttonView, isChecked ->
            // show timer picker if false
            if(isChecked){
                mService.mConnectedThread?.write("chargeWhenPlug on")
            }else{
                mService.mConnectedThread?.write("chargeWhenPlug off")
            }
        }

        backBtn.setOnClickListener {

            finish()
        }

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun setInitValue(isWhenFinish : Boolean, isWhenPlug : Boolean){
        val closeWhenfinish : Switch = findViewById(R.id.closeWhenfinish)
        val chargeWhenPlug : Switch = findViewById(R.id.chargeWhenPlug)
        closeWhenfinish.isChecked = isWhenFinish
        chargeWhenPlug.isChecked = isWhenPlug


    }


}
