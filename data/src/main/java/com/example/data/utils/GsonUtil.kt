package com.example.data.utils

import com.example.data.remote.response.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.convertToErrorResponse(): ErrorResponse =
    Gson().fromJson(this, object : TypeToken<ErrorResponse>() {}.type)
