package com.example.core.utils

fun UShort.toDp(density: Float): UShort {
    return Math.ceil(this.toDouble() / density.toDouble()).toInt().toUShort()
}

fun Int.toDp(density: Float): Int {
    return Math.ceil(this.toDouble() / density.toDouble()).toInt()
}

fun Float.toPX(density: Float): UShort {
    return Math.ceil(this.toDouble() * density.toDouble()).toInt().toUShort()
}

fun Int.toPX(density: Float): UShort {
    return Math.ceil(this.toDouble() * density.toDouble()).toInt().toUShort()
}