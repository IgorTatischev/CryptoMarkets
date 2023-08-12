package com.example.coinview.application

import android.app.Application
import com.example.coinview.di.dataModule
import com.example.coinview.di.domainModule
import com.example.coinview.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(listOf(dataModule, domainModule, viewModelModule))
        }
    }
}