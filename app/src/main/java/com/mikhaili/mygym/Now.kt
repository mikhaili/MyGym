package com.mikhaili.mygym

interface Now {
    fun time(): Long
    class Base() : Now {
        override fun time(): Long = System.currentTimeMillis()
    }
}
