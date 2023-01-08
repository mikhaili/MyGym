package com.mikhaili.mygym

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainRepositoryTest {
    @Before
    fun setUp() {

    }

    @Test
    fun test_days_saved_in_datasource() {
        val cacheDataSource = FakeDataSource()
        val now = FakeNow.Base()
        val repository = MainRepository.Base(cacheDataSource, now)
        now.addTime(1544)
        val actual = repository.days()
        val expected = 0
        assertEquals(expected, actual)
        assertEquals(1544, cacheDataSource.time(-1))
    }

    @Test
    fun test_days() {
        val cacheDataSource = FakeDataSource()
        cacheDataSource.save(Days(2).toMill())
        val now = FakeNow.Base()
        val sud = MainRepository.Base(cacheDataSource, now)
        now.addTime(Days(9).toMill())

        assertEquals(7, sud.days())
    }

    @Test
    fun test_reset() {
        val cacheDataSource = FakeDataSource()
        cacheDataSource.save(Days(2).toMill())
        val now = FakeNow.Base()
        val sud = MainRepository.Base(cacheDataSource, now)
        now.addTime(1544)

        sud.reset()
        assertEquals(1544, cacheDataSource.time(-1L))
        assertEquals(0, sud.days())
    }
}

private class Days(private val days: Int) {
    fun toMill(): Long {
        return days * 24 * 3600L * 1000L
    }
}

private interface FakeNow : Now {
    fun addTime(time: Long)
    class Base() : FakeNow {
        private var time = 0L
        override fun time(): Long = this.time

        override fun addTime(diff: Long) {
            this.time += diff
        }
    }
}

private class FakeDataSource : CacheDataSource {

    private var time: Long = -100L
    override fun save(time: Long) {
        this.time = time;
    }

    override fun time(default: Long): Long {
        if (time == -100L)
            return default
        return this.time
    }
}
