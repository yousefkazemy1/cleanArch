package com.example.core.utils

import java.io.BufferedReader
import java.io.InputStream

fun InputStream.convertToString(): String {
    val content = StringBuilder()
    BufferedReader(reader()).use { reader ->
        var line = reader.readLine()
        while (line != null) {
            content.append(line)
            line = reader.readLine()
        }
    }

    return content.toString()
}