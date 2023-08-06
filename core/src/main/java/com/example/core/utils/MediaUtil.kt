package com.example.core.utils

fun getScaledMediaSize(
    screenSize: Pair<UShort, UShort>,
    mediaSize: Pair<UShort, UShort>,
    extraHeight: UShort = 0u,
): Pair<UShort, UShort> {
    val screenWidth = screenSize.first.toDouble()
    val screenHeight = (screenSize.second - extraHeight).toDouble()
    val mediaWidth = mediaSize.first.toDouble()
    val mediaHeight = mediaSize.second.toDouble()

    if (screenWidth == 0.0 || screenHeight == 0.0 || mediaWidth == 0.0 || mediaHeight == 0.0) {
        return Pair(0u, 0u)
    }

    var scaledWidth = screenWidth
    var scaledHeight = Math.round(screenWidth * mediaHeight / mediaWidth).toDouble()

    if (scaledHeight > screenHeight) {
        scaledHeight = screenHeight
        scaledWidth = Math.round((screenSize.second.toDouble() + extraHeight.toDouble()) * mediaWidth / mediaHeight).toDouble()
    }

    return Pair(scaledWidth.toInt().toUShort(), scaledHeight.toInt().toUShort())
}