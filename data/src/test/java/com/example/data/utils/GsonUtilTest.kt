package com.example.data.utils

import com.example.data.Utils
import com.example.data.remote.response.ErrorResponse
import org.junit.Assert
import org.junit.Test

class GsonUtilTest {

    @Test
    fun convertJsonToErrorResponseModel_successfully() {
        val expectedResult = ErrorResponse(
            error = "error",
            errors = null,
            message = "message"
        )
        val content = Utils.readFileResourse("/error_response.json")
        val actualResult = content.convertToErrorResponse()

        Assert.assertEquals(expectedResult, actualResult)
    }
}