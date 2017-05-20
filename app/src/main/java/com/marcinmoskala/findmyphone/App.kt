package com.marcinmoskala.findmyphone

import android.app.Application
import android.support.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import io.fabric.sdk.android.Fabric

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceHolder.setContext(applicationContext)
        Fabric.with(this, Crashlytics())
        MultiDex.install(this);
    }
}