package com.example.huckathon

import android.app.Application
import com.example.huckathon.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class huckathon:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@huckathon)
            modules(appModule)
        }
    }
}