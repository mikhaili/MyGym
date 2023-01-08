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
        val viewModel = (application as ProviderViewModel).provideMainViewModel()

        val textView = findViewById<TextView>(R.id.mainTextView)
        val resetButton = findViewById<Button>(R.id.resetButton)

        viewModel.observe(this) { uiState ->
            uiState.apply(textView, resetButton)
        }

        resetButton.setOnClickListener(View.OnClickListener {
            viewModel.reset()
        })

        viewModel.init(savedInstanceState == null)
    }
}