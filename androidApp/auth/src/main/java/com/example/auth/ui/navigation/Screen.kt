package com.example.auth.ui.navigation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object SignUpScreen: Screen("register_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}