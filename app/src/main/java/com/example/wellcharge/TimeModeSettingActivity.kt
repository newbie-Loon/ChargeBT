package com.example.wellcharge

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder

class TimeModeSettingActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService
    private var hourItemLs = ArrayList<String>()
    private var minItemLs = ArrayList<String>()
    private var selectHour : String? = null
    private var selectMin : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mService = ServiceHolder.getService()!!
        setContentView(R.layout.time_mode_setting)

        val autoTimeOnOffMode : Switch = findViewById(R.id.autoTimeOnOffMode)
        val backBtn : Button = findViewById(R.id.backBtn)
        val hourTh: TextView = findViewById(R.id.hourTh)
        val minTH: TextView = findViewById(R.id.minTh)
        val timeSeparate: TextView = findViewById(R.id.timeSeparate)
        val spinnerHour: Spinner = findViewById(R.id.spinnerHour)
        val spinnerMin: Spinner = findViewById(R.id.spinnerMin)

        setInitValue(true)


        autoTimeOnOffMode.setOnCheckedChangeListener { buttonView, isChecked ->
            // show timer picker if false
            if(isChecked){
                setStopWatchVisible(false)
                mService.mConnectedThread?.write("autoTime on")

            }else{
                setStopWatchVisible(true)
            }
        }

        backBtn.setOnClickListener {

            finish()
        }

        spinnerHour.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            @SuppressLint("MissingPermission")
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if(hourItemLs[position] != "hour"){
                    selectHour = hourItemLs[position]
                    if(selectMin != "min" && selectMin != null){
                        mService.mConnectedThread?.write("setTimeMode off $selectHour:$selectMin")

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        spinnerMin.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            @SuppressLint("MissingPermission")
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if(minItemLs[position] != "min"){
                    selectMin = minItemLs[position]
                    if(selectHour != "hour" && selectHour != null){
                        mService.mConnectedThread?.write("setTimeMode off $selectHour:$selectMin")

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

    }

    private fun setStopWatchVisible(isVisible: Boolean) {
        val hourTh: TextView = findViewById(R.id.hourTh)
        val minTH: TextView = findViewById(R.id.minTh)
        val timeSeparate: TextView = findViewById(R.id.timeSeparate)
        val spinnerHour: Spinner = findViewById(R.id.spinnerHour)
        val spinnerMin: Spinner = findViewById(R.id.spinnerMin)
        if (isVisible) {
            hourTh.visibility = View.VISIBLE
            minTH.visibility = View.VISIBLE
            timeSeparate.visibility = View.VISIBLE
            spinnerHour.visibility = View.VISIBLE
            spinnerMin.visibility = View.VISIBLE
            hourItemLs = ArrayList<String>()
            minItemLs = ArrayList<String>()
            hourItemLs.add("hour")
            minItemLs.add("min")
            for (i in 0..24) {
                hourItemLs.add("$i")
            }
            for (i in 0..60) {
                if(i%5 == 0){
                    minItemLs.add("$i")
                }
            }
            val adapterHour = ArrayAdapter(
                this,
                R.layout.spinner_time_textsize, hourItemLs
            )
            spinnerHour.adapter = adapterHour
            val adapterMin = ArrayAdapter(
                this,
                R.layout.spinner_time_textsize, minItemLs
            )
            spinnerMin.adapter = adapterMin
        } else {
            hourTh.visibility = View.INVISIBLE
            minTH.visibility = View.INVISIBLE
            timeSeparate.visibility = View.INVISIBLE
            spinnerHour.visibility = View.INVISIBLE
            spinnerMin.visibility = View.INVISIBLE
        }
    }

    private fun setInitValue(isAutoChecked : Boolean){
        val spinnerHour: Spinner = findViewById(R.id.spinnerHour)
        val spinnerMin: Spinner = findViewById(R.id.spinnerMin)
        val autoTimeOnOffMode : Switch = findViewById(R.id.autoTimeOnOffMode)
        autoTimeOnOffMode.isChecked = isAutoChecked
        if(isAutoChecked){

            setStopWatchVisible(false)
        }else{
            setStopWatchVisible(true)
//            spinnerHour.setSelection(0)
//            spinnerMin.setSelection(0)
        }

    }

}
