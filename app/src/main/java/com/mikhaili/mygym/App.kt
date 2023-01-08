package com.mikhaili.mygym

import android.app.Application
import androidx.lifecycle.MutableLiveData

class App : Application(), ProviderViewModel {
    private lateinit var viewModel: MainViewModel;
    override fun onCreate() {
        super.onCreate()

        viewModel = MainViewModel(
            MainRepository.Base(
                cacheDataSource = CacheDataSource.Base(
                    sharedPreferences = SharedPref.Factory(BuildConfig.DEBUG).make(this)
                ),
                Now.Base()
            ),
            communication = MainCommunication.Base(MutableLiveData())
        )
    }

    override fun provideMainViewModel(): MainViewModel {
        return viewModel
    }


}

interface ProviderViewModel {
    fun provideMainViewModel(): MainViewModel
}

