package com.mikhaili.mygym

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainViewModelTest {
    @Test
    fun test_reset() {
        val repo = FakeRepository.Base(5)
        val communication = FakeMainCommunication.Base()

        val viewModel = MainViewModel(repo, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.NDays(5)))
        viewModel.reset()
        assertEquals(true, repo.checkResetCalledCount(1))
        assertEquals(true, communication.checkCalledCount(2))
        assertEquals(true, communication.isSame(UiState.ZeroDays()))
    }

    @Test
    fun test_0_days_and_reinit() {
        val repo = FakeRepository.Base(0)
        val communication = FakeMainCommunication.Base()

        val viewModel = MainViewModel(repo, communication)
        viewModel.init(isFirstRun = true)
        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.ZeroDays()))

        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }

    @Test
    fun test_N_days_and_reinit() {
        val repo = FakeRepository.Base(5)
        val communication = FakeMainCommunication.Base()

        val viewModel = MainViewModel(repo, communication)
        viewModel.init(isFirstRun = true)

        assertEquals(true, communication.checkCalledCount(1))
        assertEquals(true, communication.isSame(UiState.NDays(5)))

        viewModel.init(isFirstRun = false)
        assertEquals(true, communication.checkCalledCount(1))
    }
}

private interface FakeRepository : MainRepository {

    class Base(private val days: Int) : FakeRepository {
        private var resetCalledCount = 0

        override fun days(): Int {
            return days;
        }

        override fun reset() {
            resetCalledCount++
        }

        fun checkResetCalledCount(calledCount: Int): Any? {
            return resetCalledCount == calledCount
        }
    }
}

private interface FakeMainCommunication : MainCommunication.Mutable {
    fun isSame(value: UiState): Boolean
    fun checkCalledCount(count: Int): Boolean

    class Base : FakeMainCommunication {
        private var callCount = 0
        private lateinit var state: UiState
        override fun isSame(value: UiState): Boolean = state == value

        override fun checkCalledCount(count: Int): Boolean = count == callCount

        override fun put(value: UiState) {
            callCount++
            state = value
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) = Unit
    }
}
