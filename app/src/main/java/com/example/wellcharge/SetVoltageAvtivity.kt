package com.example.wellcharge

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder

class SetVoltageAvtivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_voltage)

        mService = ServiceHolder.getService()!!

        val a6 : ImageButton = findViewById(R.id.a6)
        val a13 : ImageButton = findViewById(R.id.a13)
        val a10 : ImageButton = findViewById(R.id.a10)
        val a16 : ImageButton = findViewById(R.id.a16)
        val a20 : ImageButton = findViewById(R.id.a20)
        val a25 : ImageButton = findViewById(R.id.a25)
        val a32 : ImageButton = findViewById(R.id.a32)

        a6.setOnClickListener {
            mService.mConnectedThread?.write("check a6")
//            showToast("")
            finish()
        }
        a10.setOnClickListener {
            mService.mConnectedThread?.write("check a10")
//            showToast("")
            finish()
        }
        a13.setOnClickListener {
            mService.mConnectedThread?.write("check a13")
//            showToast("")
            finish()
        }
        a16.setOnClickListener {
            mService.mConnectedThread?.write("check 16")
//            showToast("")
            finish()
        }
        a20.setOnClickListener {
            mService.mConnectedThread?.write("check 20")
//            showToast("")
            finish()
        }
        a25.setOnClickListener {
            mService.mConnectedThread?.write("check 25")
//            showToast("")
            finish()
        }
        a32.setOnClickListener {
            mService.mConnectedThread?.write("check 32")
//            showToast("")
            finish()
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
