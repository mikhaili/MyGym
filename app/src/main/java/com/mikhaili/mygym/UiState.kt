package com.mikhaili.mygym

import android.view.View
import android.widget.Button
import android.widget.TextView

sealed class UiState {
    abstract fun apply(dayTextView: TextView, resetBtn: Button)
    data class ZeroDays(private val days: Int = 0) : UiState() {
        override fun apply(dayTextView: TextView, resetBtn: Button) {
            dayTextView.text = days.toString()
            resetBtn.visibility = View.GONE
        }

    }

    data class NDays(private val days: Int) : UiState() {
        override fun apply(dayTextView: TextView, resetBtn: Button) {
            dayTextView.text = days.toString()
            resetBtn.visibility = View.VISIBLE
        }
    }
}