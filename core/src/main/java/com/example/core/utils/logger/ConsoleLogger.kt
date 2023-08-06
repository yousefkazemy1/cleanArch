package com.example.core.utils.logger

class ConsoleLogger : Logger {
    override fun log(vararg message: String) {
        val finalMessage = buildString {
            message.forEachIndexed { index, msg ->
                append(msg)
                if (index < message.size - 1) {
                    append(" | ")
                }
            }
        }
        println(finalMessage)
    }
}