package com.example.core.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point

var screenSize = Point()
var density = 1f
var scaledDensity = 1f

fun checkScreenSize(context: Context, newConfiguration: Configuration? = null) {
    density = context.getResources().getDisplayMetrics().density
    scaledDensity = context.getResources().getDisplayMetrics().density
    var configuration: Configuration? = newConfiguration
    if (configuration == null) {
        configuration = context.getResources().getConfiguration()
    }

    if (configuration!!.screenWidthDp != Configuration.SCREEN_WIDTH_DP_UNDEFINED) {
        val newSize = Math.ceil((configuration.screenWidthDp * density).toDouble()).toInt()
        if (Math.abs(screenSize.x - newSize) > 3) {
            screenSize.x = newSize
        }
    }

    if (configuration.screenHeightDp != Configuration.SCREEN_HEIGHT_DP_UNDEFINED) {
        val newSize = Math.ceil((configuration.screenHeightDp * density).toDouble()).toInt()
        if (Math.abs(screenSize.y - newSize) > 3) {
            screenSize.y = newSize
        }
    }

    println("checkScreenSize: ${screenSize.x} | ${screenSize.y}")
}