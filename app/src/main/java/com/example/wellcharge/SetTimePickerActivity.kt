package com.example.wellcharge

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class SetTimePickerActivity : ComponentActivity() {
    private lateinit var mService: BTKeepConnService
    @RequiresApi(Build.VERSION_CODES.O)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val START = "start"
    private val END = "end"
    private var startH : Int? = null
    private var startM : Int? = null
    private var endH : Int? = null
    private var endM : Int? = null
    private var hourItemLs = ArrayList<String>()
    private var minItemLs = ArrayList<String>()
    private var selectHour : String? = null
    private var selectMin : String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_time_picker)
        mService = ServiceHolder.getService()!!
//        var mode : String?
        var switchStatus: String?
        val switch: Switch = findViewById(R.id.switch1)
        val back : Button = findViewById(R.id.back)
        //Timer part
        val startTimeTxt: TextView = findViewById(R.id.startTimeTxt)
        val endTimeTxt: TextView = findViewById(R.id.endTimeTxt)
        val startTime: TextView = findViewById(R.id.startTime)
        val endTime: TextView = findViewById(R.id.endTime)
        val setStartTime: Button = findViewById(R.id.setStartTime)
        val setEndTime: Button = findViewById(R.id.setEndTime)
        //StopWatch part
        val hourTh: TextView = findViewById(R.id.HourTh)
        val minTH: TextView = findViewById(R.id.MinTh)
        val timeSeparate: TextView = findViewById(R.id.timeSeparate)
        val spinnerHour: Spinner = findViewById(R.id.spinnerHour)
        val spinnerMin: Spinner = findViewById(R.id.spinnerMin)


        setTimerVisible(true)
        setStopWatchVisible(false)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchStatus = switch.textOn.toString()
                setTimerVisible(false)
                setStopWatchVisible(true)
            } else {
                switchStatus = switch.textOff.toString()
                setTimerVisible(true)
                setStopWatchVisible(false)
            }

        }

        setStartTime.setOnClickListener{
            showTimePickerDialog(START)
        }

        setEndTime.setOnClickListener {
            showTimePickerDialog(END)
        }

        back.setOnClickListener {

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
//                        mService.mConnectedThread?.write("stopWatch mode start 0:0 Duration $selectHour:$selectMin")
                        mService.mConnectedThread?.write("{\"cmd\":23,\"strStartTime\":\"0:0\",\"strDuration\":\"$selectHour:$selectMin\"}\n")

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
//                        mService.mConnectedThread?.write("stopWatch mode start 0:0 Duration $selectHour:$selectMin")
                        mService.mConnectedThread?.write("{\"cmd\":23,\"strStartTime\":\"0:0\",\"strDuration\":\"$selectHour:$selectMin\"}\n")

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


    }

    private fun setTimerVisible(isVisible: Boolean) {
        val startTimeTxt: TextView = findViewById(R.id.startTimeTxt)
        val endTimeTxt: TextView = findViewById(R.id.endTimeTxt)
        val startTime: TextView = findViewById(R.id.startTime)
        val endTime: TextView = findViewById(R.id.endTime)
        val setStartTime: Button = findViewById(R.id.setStartTime)
        val setEndTime: Button = findViewById(R.id.setEndTime)
        if (isVisible) {
            startTimeTxt.visibility = View.VISIBLE
            endTimeTxt.visibility = View.VISIBLE
            startTime.visibility = View.VISIBLE
            endTime.visibility = View.VISIBLE
            setStartTime.visibility = View.VISIBLE
            setEndTime.visibility = View.VISIBLE
            val c = Calendar.getInstance()
            val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            var hour : String? = null
            var min : String? = null
            if(hourOfDay<=9){
                hour = "0$hourOfDay"
            }else{
                hour = hourOfDay.toString()
            }
            if(minute<=9){
                min = "0$minute"
            }else{
                min = minute.toString()
            }
            startH = hourOfDay
            startM = minute
            endH = hourOfDay
            endM = minute
            startTime.text = "$hour:$min"
            endTime.text = "$hour:$min"
        } else {
            startTimeTxt.visibility = View.INVISIBLE
            endTimeTxt.visibility = View.INVISIBLE
            startTime.visibility = View.INVISIBLE
            endTime.visibility = View.INVISIBLE
            setStartTime.visibility = View.INVISIBLE
            setEndTime.visibility = View.INVISIBLE
        }
    }

    private fun setStopWatchVisible(isVisible: Boolean) {
        val hourTh: TextView = findViewById(R.id.HourTh)
        val minTH: TextView = findViewById(R.id.MinTh)
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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun showTimePickerDialog(action : String){
        val c = Calendar.getInstance()
        val startTime: TextView = findViewById(R.id.startTime)
        val endTime: TextView = findViewById(R.id.endTime)
        val hours = c.get(Calendar.HOUR_OF_DAY)
        val minutes = c.get(Calendar.MINUTE)
        val current = LocalDateTime.now()
//        val dateNow = LocalDate.now()
        var dateStart : LocalDate = LocalDate.now()
        var dateEnd : LocalDate = LocalDate.now()

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                var hour : String? = null
                var min : String? = null
                if(hourOfDay<=9){
                    hour = "0$hourOfDay"
                }else{
                    hour = hourOfDay.toString()
                }
                if(minute<=9){
                    min = "0$minute"
                }else{
                    min = minute.toString()
                }
                if(action == START){
                    startTime.text = "$hour:$min"
                    startH = hourOfDay
                    startM = minute
                    if(endH != null && endM != null){
                        // Calculate duration
//                        if(LocalDateTime.of(dateStart, LocalTime.of(startH!!, startM!!)) < current){
//                            // set date start +1
//                            dateStart.plusDays(1)
//                        }
                        if(LocalDateTime.of(dateEnd, LocalTime.of(endH!!, endM!!))
                                .isBefore(LocalDateTime.of(dateStart, LocalTime.of(startH!!, startM!!)))){
                            // set date end +1
                            dateEnd = dateEnd.plusDays(1)
                        }
                        val duration = Duration.between(LocalDateTime.of(dateStart, LocalTime.of(startH!!, startM!!))
                            , LocalDateTime.of(dateEnd, LocalTime.of(endH!!, endM!!)))
                        val endHours = duration.toHours() % 24
                        val endMinutes = duration.toMinutes() % 60
                        mService.mConnectedThread?.write("{\"cmd\":23,\"strStartTime\":\"$startH:$startM\",\"strDuration\":\"$endHours:$endMinutes\"}\n")

//                        mService.mConnectedThread?.write("Timer mode Start $startH:$startM, Duration $endHours:$endMinutes")

                    }
//                    ********************************

                }else if(action == END){
                    endTime.text = "$hour:$min"
                    endH = hourOfDay
                    endM = minute
                    if(startH != null && startM != null){
//                        if(LocalDateTime.of(dateStart, LocalTime.of(startH!!, startM!!)) >
//                            LocalDateTime.of(dateEnd, LocalTime.of(endH!!, endM!!))){
//                            // set date end +1
//                            dateEnd.plusDays(1)
//                        }
                        if(LocalDateTime.of(dateEnd, LocalTime.of(endH!!, endM!!))
                            .isBefore(LocalDateTime.of(dateStart, LocalTime.of(startH!!, startM!!)))){
                            dateEnd = dateEnd.plusDays(1)
                        }
                        val duration = Duration.between(LocalDateTime.of(dateStart, LocalTime.of(startH!!, startM!!))
                            , LocalDateTime.of(dateEnd, LocalTime.of(endH!!, endM!!)))
                        val endHours = duration.toHours() % 24
                        val endMinutes = duration.toMinutes() % 60

//                        {
//                            "cmd":23,
//                            "strStartTime":"xx:xx",
//                            "strDuration":"xx:xx"
//                        }
//                        "{\"cmd\":23,\"strStartTime\":\"xx:xx\",\"strDuration\":\"xx:xx\"}"
                        mService.mConnectedThread?.write("{\"cmd\":23,\"strStartTime\":\"$startH:$startM\",\"strDuration\":\"$endHours:$endMinutes\"}\n")

//                        mService.mConnectedThread?.write("Timer mode Start $startH:$startM, Duration $endHours:$endMinutes")

                    }
//                    ********************************

                }
            },
            hours,
            minutes,
            false
        )
        timePickerDialog.show()
    }

    private fun showDatePickerDialog(action : String){
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR);
        val mMonth = c.get(Calendar.MONTH);
        val mDay = c.get(Calendar.DAY_OF_MONTH);
        val datePickerDialog = DatePickerDialog(this,
            { view, year, monthOfYear, dayOfMonth ->
                if(action == START){

                }else if(action == END){

                }
//                txtDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
