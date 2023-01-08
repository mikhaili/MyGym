package com.mikhaili.mygym

import android.content.Context
import android.content.SharedPreferences

interface SharedPref {
    fun make(context: Context): SharedPreferences

    abstract class Abstract(
        private val name: String,
        private val mode: Int = Context.MODE_PRIVATE
    ) : SharedPref {
        override fun make(context: Context): SharedPreferences = context.getSharedPreferences(
            name,
            mode
        )
    }

    class Release : Abstract("releaseData")
    class Debug : Abstract("debugData")
    class UiTest : Abstract("testData")
    class Factory(private val userTest: Boolean) : SharedPref {
        override fun make(context: Context): SharedPreferences =
            (if (userTest) Debug() else Release()).make(context)
    }
}