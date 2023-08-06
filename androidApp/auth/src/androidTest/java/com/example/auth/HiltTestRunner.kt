package com.example.auth

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.core.base.BaseApplication
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}

@CustomTestApplication(BaseApplication::class)
interface CustomTestApplicationForHilt