package com.mikhaili.mygym

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainViewModelTest {
    @Test
    fun test_0_days_and_reinit() {
        val repo = FakeRepository(0)
        val communication = FakeMainCommunication.Base()

        val viewModel = MainViewModel(repo, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.ZeroDays))

        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }

    @Test
    fun test_N_days_and_reinit() {
        val repo = FakeRepository(5)
        val communication = FakeMainCommunication.Base()

        val viewModel = MainViewModel(repo, communication)
        viewModel.init(isFirstRun = true)

        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.NDays(5)))

        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }
}

class FakeRepository(private val days: Int) : MainRepository {
    override fun days(): Int {
        return days;
    }
}

interface FakeMainCommunication : MainCommunication.Put {
    fun isSame(value: UiState): Boolean
    fun checkCalledCount(): Int

    class Base : FakeMainCommunication {
        private var callCount = 0
        private lateinit var state: UiState
        override fun isSame(value: UiState): Boolean = state == value

        override fun checkCalledCount(count: Int): Boolean = count == callCount

        override fun put(value: UiState) {
            callCount++
            state = value
        }
    }
}
