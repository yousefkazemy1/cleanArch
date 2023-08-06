package com.example.cleanarchproject

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import com.example.cleanarchproject.ui.main.MainActivity
import com.example.core.base.BaseApplication
import com.example.core.base.EntryPointHolder
import com.example.core.utils.checkScreenSize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : BaseApplication(), EntryPointHolder {

    override fun getMainIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        checkScreenSize(context = applicationContext)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        checkScreenSize(context = applicationContext, newConfiguration = newConfig)
    }
}