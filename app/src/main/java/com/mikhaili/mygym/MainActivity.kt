package com.mikhaili.mygym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.mainTextView)
        val resetButton = findViewById<Button>(R.id.resetButton)

        val sharedPreferences = getSharedPreferences("main", MODE_PRIVATE)
        val time = sharedPreferences.getLong("time", -1L)
        if (time == -1L) {
            sharedPreferences.edit().putLong("time", System.currentTimeMillis()).apply()
            textView.text = "0"
            resetButton.visibility = View.GONE
        } else {
            val diff = (System.currentTimeMillis() - time) / 1000
            val days = diff / (24 * 3600)
            if (days == 0L) {
                resetButton.visibility = View.GONE
            }
            textView.text = days.toString()
        }

        resetButton.setOnClickListener(View.OnClickListener {
            sharedPreferences.edit().putLong("time", System.currentTimeMillis()).apply()
            textView.text = "0"
            resetButton.visibility = View.GONE
        })
    }
}