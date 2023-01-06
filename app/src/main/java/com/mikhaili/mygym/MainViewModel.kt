package com.mikhaili.mygym

import android.provider.CalendarContract.EventDays
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MainViewModel(
    private val repository: MainRepository,
    private val communication: MainCommunication.Mutable
) : MainCommunication.Observe {
    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            val days = repository.days()
            val value: UiState = if (days == 0)
                UiState.ZeroDays()
            else
                UiState.NDays(days)

            communication.put(value)
        }
    }

    override fun observer(owner: LifecycleOwner, observer: Observer<UiState>) {
        communication.observer(owner, observer)
    }

    fun reset() {
        repository.reset()
        communication.put(UiState.ZeroDays())
    }
}

interface MainRepository {
    fun days(): Int
    fun reset()
}

interface MainCommunication {
    interface Put {
        fun put(value: UiState)
    }

    interface Observe {
        fun observer(owner: LifecycleOwner, observer: Observer<UiState>)
    }

    interface Mutable : Put, Observe

    class Base(private val liveData: MutableLiveData<UiState>) :
        Mutable {
        override fun put(value: UiState) {
            liveData.value = value
        }

        override fun observer(owner: LifecycleOwner, observer: Observer<UiState>) =
            liveData.observe(owner, observer)
    }
}

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