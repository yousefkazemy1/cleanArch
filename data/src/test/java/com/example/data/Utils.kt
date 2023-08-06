package com.example.data

import java.io.InputStreamReader

object Utils {

    fun readFileResourse(fileName: String): String {
        val inputStream = Utils::class.java.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        reader.close()

        return builder.toString()
    }
}