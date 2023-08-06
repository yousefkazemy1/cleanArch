package com.example.auth.utils.ui

import androidx.compose.material.ExperimentalMaterialApi
import com.example.core.utils.SnackbarController
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
fun getSnackbarController(scope: CoroutineScope): SnackbarController {
    return SnackbarController(scope)
}