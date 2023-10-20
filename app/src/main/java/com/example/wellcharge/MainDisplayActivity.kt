package com.example.wellcharge

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.wellcharge.service.BTKeepConnService
import com.example.wellcharge.service.ServiceHolder
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InvalidClassException
import java.io.NotSerializableException

class MainDisplayActivity : ComponentActivity() {

    private lateinit var mService: BTKeepConnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_display)
        val setI: ImageButton = findViewById(R.id.setI)
        val setT: ImageButton = findViewById(R.id.setT)
        val setEtc: ImageButton = findViewById(R.id.setEtc)
        val setStop: ImageButton = findViewById(R.id.setStop)
        val voltage :TextView = findViewById(R.id.voltage)
        val current :TextView = findViewById(R.id.current)
        val plugTemp :TextView = findViewById(R.id.plugTemp)
        val relayTemp :TextView = findViewById(R.id.relayTemp)
        val time :TextView = findViewById(R.id.time)
        time.visibility = View.INVISIBLE

        mService = ServiceHolder.getService()!!
//        mService.mConnectedThread?.write("check")

        setReadThread()

        setI.setOnClickListener {
            val intent = Intent(applicationContext, SetVoltageAvtivity::class.java)
            startActivity(intent);
        }
        setT.setOnClickListener {
            val intent = Intent(applicationContext, SetTimePickerActivity::class.java)
            startActivity(intent);
        }
        setEtc.setOnClickListener {
            val intent = Intent(applicationContext, EtcSettingActivity::class.java)
            startActivity(intent);
        }
        setStop.setOnClickListener {
            mService.mConnectedThread?.write("check stop")
        }
    }

    private fun setReadThread() {
        val printThread = Thread {
            while (true) {
                getData()
                Thread.sleep(1000)
            }
        }
        printThread.start()
    }


    private fun getData() {
        val directory: File? = this.getExternalFilesDir(null)
        val subdirectory: File = File(directory, "/dataLogger")
        val jsonArray = JSONArray()

        val isDirectoryExists = if (!subdirectory.exists()) {
            subdirectory.mkdirs()

        } else true
        if (isDirectoryExists) {
            val destination: File = File(subdirectory, "report.txt")
            try {
                val fin = FileInputStream(destination).bufferedReader()
                if (fin.ready()) {
                    while (fin.ready()) {
                        var line: String = fin.readLine()
                        val jsonObject = JSONObject(line)
                        jsonArray.put(jsonObject)
                    }
                    fin.close()
                runOnUiThread {
                getlastValue(jsonArray)
                }
                }
            } catch (e: NullPointerException) {
                // Handle an error
            } catch (e: FileNotFoundException) {
                // Handle an error
            } catch (e: SecurityException) {
                // Handle an error
            } catch (e: IOException) {
                // Handle an error
            } catch (e: InvalidClassException) {
                // Handle an error
            } catch (e: NotSerializableException) {
                // Handle an error
            }
        }
    }

    private fun getlastValue(dataLogger: JSONArray) {
        val voltage: TextView = findViewById(R.id.voltage)
        val current: TextView = findViewById(R.id.current)
        val plugTemp: TextView = findViewById(R.id.plugTemp)
        val relayTemp: TextView = findViewById(R.id.relayTemp)
//        val time :TextView = findViewById(R.id.time)
        val line = dataLogger.length() - 1
        voltage.text = dataLogger.getJSONObject(line).getDouble("fVoltage").toString()
        current.text = dataLogger.getJSONObject(line).getDouble("fCurrent").toString()
        plugTemp.text = dataLogger.getJSONObject(line).getDouble("fTempPlug").toString()
        relayTemp.text = dataLogger.getJSONObject(line).getDouble("fTempRelay").toString()
//        time.text = dataLogger.getJSONObject(line).getDouble("TIME").toString()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
