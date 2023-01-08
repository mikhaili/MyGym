package com.mikhaili.mygym

import android.content.SharedPreferences
import java.util.concurrent.TimeUnit

interface MainRepository {
    fun days(): Int
    fun reset()

    class Base(private val cacheDataSource: CacheDataSource, private val now: Now) :
        MainRepository {
        override fun days(): Int {
            val saved = cacheDataSource.time(-1L)
            if (saved == -1L) {
                cacheDataSource.save(now.time())
                return 0;
            }
            return TimeUnit.MILLISECONDS.toDays(now.time() - saved).toInt()
        }

        override fun reset() {
            cacheDataSource.save(now.time())
        }

    }
}

interface CacheDataSource {

    fun time(default: Long): Long
    fun save(time: Long)

    class Base(private val sharedPreferences: SharedPreferences) : CacheDataSource {
        override fun time(default: Long): Long {
            return sharedPreferences.getLong(KEY, default)
        }

        override fun save(time: Long) {
            sharedPreferences.edit().putLong(KEY, time).apply()
        }

        companion object {
            private const val KEY = "savedTimeKey"

        }
    }
}
