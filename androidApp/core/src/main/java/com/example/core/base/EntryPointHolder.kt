package com.example.core.base

import android.content.Context
import android.content.Intent

interface EntryPointHolder {
    fun getMainIntent(context: Context): Intent
}