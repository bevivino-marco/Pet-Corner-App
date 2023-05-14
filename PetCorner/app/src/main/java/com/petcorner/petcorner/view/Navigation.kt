package com.petcorner.petcorner.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.petcorner.petcorner.view.*

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "animals") {
        composable("animals") {
            AnimalsScreen()
        }
        composable("users") {
            UsersScreen()
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegistrationScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }

    }
}