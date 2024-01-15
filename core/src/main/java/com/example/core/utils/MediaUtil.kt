package com.example.core.utils

import kotlin.math.round

fun getScaledMediaSize(
    screenSize: Pair<UShort, UShort>,
    mediaSize: Pair<UShort, UShort>,
    extraHeight: UShort = 0u,
): Pair<UShort, UShort> {
    if (!validateScreenAndMediaSize(screenSize = screenSize, mediaSize = mediaSize)) return Pair(
        0u, 0u
    )

    return getScaledSize(
        screenSize = screenSize,
        mediaSize = mediaSize,
        extraHeight = extraHeight
    )
}

fun validateScreenAndMediaSize(
    screenSize: Pair<UShort, UShort>,
    mediaSize: Pair<UShort, UShort>,
): Boolean {
    if (screenSize.validateDisplaySize() && mediaSize.validateDisplaySize()) {
        return true
    }
    return false
}

fun Pair<UShort, UShort>.validateDisplaySize(): Boolean {
    if (first == 0u.toUShort() || second == 0u.toUShort()) {
        return false
    }
    return true
}

fun getScaledSize(
    screenSize: Pair<UShort, UShort>,
    mediaSize: Pair<UShort, UShort>,
    extraHeight: UShort = 0u,
): Pair<UShort, UShort> {
    val screenWidth = screenSize.first.toDouble()
    val screenHeight = (screenSize.second - extraHeight).toDouble()
    val mediaWidth = mediaSize.first.toDouble()
    val mediaHeight = mediaSize.second.toDouble()

    var scaledWidth = screenWidth
    var scaledHeight = getScaledHeight(
        screenWidth = screenWidth, mediaWidth = mediaWidth, mediaHeight = mediaHeight
    )

    if (scaledHeight > screenHeight) {
        scaledHeight = screenHeight
        scaledWidth = getScaledWidth(
            screenHeight = screenHeight, mediaWidth = mediaWidth, mediaHeight = mediaHeight
        )
    }

    return Pair(scaledWidth.toInt().toUShort(), scaledHeight.toInt().toUShort())
}

fun getScaledHeight(
    screenWidth: Double,
    mediaWidth: Double,
    mediaHeight: Double,
) = round(screenWidth * mediaHeight / mediaWidth).toDouble()

fun getScaledWidth(
    screenHeight: Double,
    mediaWidth: Double,
    mediaHeight: Double,
) = round(screenHeight * mediaWidth / mediaHeight).toDouble()