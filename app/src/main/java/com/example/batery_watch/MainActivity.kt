package com.example.batery_watch

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    fun readFile() {

        val fis: FileInputStream
        val storedString = StringBuffer()

        try {
            var path = "/sys/devices/pci0000:00/0000:00:00.4/i2c-1/1-0036/power_supply/max170xx_battery/uevent";
            val inputStream: InputStream = File(path).inputStream()


            val inputString = inputStream.bufferedReader().use { it.readText() }
            var helloTextView = findViewById<TextView>(R.id.text_view_id);
            helloTextView.text = inputString;
        } catch (e: Exception) {
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readFile()

        var button = findViewById<Button>(R.id.button_id)
        button.setOnClickListener{
            readFile()
        }


    }
}