package com.example.core

import com.example.core.utils.convertToString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class StreamUtilTest {

    @Test
    fun convertInputStream_toString_correctly() {
        val mockString = "Hello, this is a mock input stream!"
        val mockData = mockString.toByteArray()

        val byteInputStream = ByteArrayInputStream(mockData)

        val convertedString = byteInputStream.convertToString()

        Assertions.assertEquals(convertedString, mockString)
    }
}