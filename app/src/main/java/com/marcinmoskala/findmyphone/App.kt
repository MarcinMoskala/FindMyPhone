package com.marcinmoskala.findmyphone

import android.app.Application
import com.marcinmoskala.kotlinpreferences.PreferenceHolder

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceHolder.setContext(applicationContext)
    }
}