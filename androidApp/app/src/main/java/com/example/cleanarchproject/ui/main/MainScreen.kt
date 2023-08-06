package com.example.cleanarchproject.ui.main

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.component.ui.compose.navigation.BottomBarScreen
import com.example.core.utils.density
import com.example.core.utils.toPX

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    viewModel.checkIsUserLoggedIn()
    BottomBarGraph(navController = navController, isUserLoggined = viewModel.isUserLoggedIn())
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomBarGraph(
    navController: NavHostController,
    isUserLoggined: Boolean = false,
) {
    Scaffold(bottomBar = {
        BottomBar(navController)
    }) {
        Box(modifier = Modifier.padding(it)) {
            NavGraph(
                navController = navController,
                isLoggined = isUserLoggined,
                paddingBottom = (it.calculateBottomPadding().value.toPX(density) + 16.toPX(density)).toUShort()
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    val screens = listOf(
        BottomBarScreen.Home, BottomBarScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    when (currentDestination?.route) {
        BottomBarScreen.Home.route, BottomBarScreen.Profile.route -> {
            if (!bottomBarState.value) {
                bottomBarState.value = true
            }
        }
        else -> {
            if (bottomBarState.value) {
                bottomBarState.value = false
            }
        }
    }

    AnimatedVisibility(visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        })
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    BottomNavigationItem(label = {
        Text(text = screen.title)
    }, icon = {
        Icon(
            imageVector = screen.icon, contentDescription = screen.title
        )
    }, selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true, onClick = {
        if (screen.route != currentDestination?.route) {
            navController.navigate(screen.route) {
                popUpTo(currentDestination?.route!!) {
                    inclusive = true
                }
            }
        }
    })
}