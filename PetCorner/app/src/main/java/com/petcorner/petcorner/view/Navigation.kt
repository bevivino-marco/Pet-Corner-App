package com.petcorner.petcorner.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.petcorner.petcorner.view.*
import com.petcorner.petcorner.viewmodel.AnimalViewModel
import com.petcorner.petcorner.viewmodel.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController, animalViewModel: AnimalViewModel, profileViewModel: ProfileViewModel) {
    NavHost(navController = navController, startDestination = "profile") {
        composable("animals") {
            AnimalsScreen(animalViewModel)
        }
        composable("users") {
            UsersScreen()
        }
        composable("login") {
            LoginScreen(navController, profileViewModel)
        }
        composable("register") {
            RegistrationScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController, animalViewModel, profileViewModel )
        }
        composable("add-animal") {
            NewAnimalForm(navController, animalViewModel, profileViewModel )
        }

    }
}