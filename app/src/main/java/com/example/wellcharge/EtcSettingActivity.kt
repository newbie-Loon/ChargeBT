package com.example.wellcharge

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder

class EtcSettingActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.etc_setting)
        mService = ServiceHolder.getService()!!

        val modeSetting : Button = findViewById(R.id.modeSetting)
        val tempSetting : Button = findViewById(R.id.tempSetting)
        val soundSetting : Button = findViewById(R.id.soundSetting)
        val ledSetting : Button = findViewById(R.id.ledSetting)
        val sleepSetting : Button = findViewById(R.id.sleepSetting)
        val backBtn : Button = findViewById(R.id.backBtn)

        modeSetting.text = "    "+getString(R.string.modesetting)
        tempSetting.text = "    "+getString(R.string.tempsetting)
        soundSetting.text = "    "+getString(R.string.soundsetting)
        ledSetting.text = "    "+getString(R.string.ledsetting)
        sleepSetting.text = "    "+getString(R.string.sleepsetting)

//        showToast("strChipId: "+ SettingValue.getSetting()?.getString("strChipId"))
        sleepSetting.visibility = View.INVISIBLE
        modeSetting.setOnClickListener {
            val intent = Intent(applicationContext, ModeSettingActivity::class.java)
            startActivity(intent);
//            finish()
        }
        tempSetting.setOnClickListener {
            val intent = Intent(applicationContext, TempSettingActivity::class.java)
            startActivity(intent);
//            finish()
        }
        soundSetting.setOnClickListener {
            val intent = Intent(applicationContext, SoundSettingActivity::class.java)
            startActivity(intent);
//            finish()
        }
        ledSetting.setOnClickListener {
            val intent = Intent(applicationContext, LedSettingActivity::class.java)
            startActivity(intent);
//            finish()
        }
        sleepSetting.setOnClickListener {
            val intent = Intent(applicationContext, SleepModeActivity::class.java)
            startActivity(intent);
//            finish()
        }
        backBtn.setOnClickListener {
            finish()
        }



    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
