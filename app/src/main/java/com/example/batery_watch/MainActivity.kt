package com.example.batery_watch

import android.os.Bundle
import android.os.CountDownTimer
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


class MainActivity : AppCompatActivity() {


    val time: Long = 1000000000L
    var timer = Timer(time)
    var isStop = false


    fun readFile() {

        val fis: FileInputStream
        val storedString = StringBuffer()

        try {
            var path = "/sys/devices/pci0000:00/0000:00:00.4/i2c-1/1-0036/power_supply/max170xx_battery/uevent";
            val inputStream: InputStream = File(path).inputStream()


            val inputString = inputStream.bufferedReader().use { it.readText() }
            var showBatteryInfo = findViewById<TextView>(R.id.text_view_id);

            showBatteryInfo.text = inputString.replace("\n", "\n\n").replace("=", "=\n");
            showBatteryInfo.movementMethod = ScrollingMovementMethod()
        } catch (e: Exception) {
        }

    }

    private fun start(){
        timer.start()
    }
    private fun stop(){
        timer.cancel()
    }
    inner class Timer(miliis:Long) : CountDownTimer(miliis,1){
        var millisUntilFinished:Long = 0
        override fun onFinish() {
        }
        override fun onTick(millisUntilFinished: Long) {
            this.millisUntilFinished = millisUntilFinished
            val passTime = time - millisUntilFinished
            val hour = passTime / 3600000 % 24
            val min = passTime / 60000 % 60
            var sec = (passTime / 1000 % 60).toInt()

            val maxSec = 3

            if((sec % maxSec) + 1 == maxSec) {
                readFile()
            }

        }
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readFile()
        timer.start()

        var button = findViewById<Button>(R.id.button_id)


        button.setOnClickListener{
            if(isStop == false) {
                timer.start()
                isStop = true

                button.text = "Press to Stop"
            } else {
                timer.cancel()
                isStop = false
                button.text = "Press to Start"
            }

            readFile()
        }

    }
}