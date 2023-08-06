package com.example.cleanarchproject.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.auth.ui.login.LoginScreen
import com.example.auth.ui.navigation.Screen
import com.example.auth.ui.signup.SignUpScreen
import com.example.cleanarchproject.ui.HomeScreen
import com.example.core.component.ui.compose.navigation.BottomBarScreen
import com.example.profile.ui.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    isLoggined: Boolean = false,
    paddingBottom: UShort = 0u,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggined) BottomBarScreen.Home.route else Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController, paddingBottom = paddingBottom)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}