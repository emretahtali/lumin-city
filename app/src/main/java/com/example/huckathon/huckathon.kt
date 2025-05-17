package com.example.huckathon

import android.app.Application
import com.example.huckathon.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class huckathon:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@huckathon)
            modules(appModule)
        }
    }
}