package com.petcorner.petcorner

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.petcorner.petcorner.ui.theme.Navigation
import com.petcorner.petcorner.ui.theme.PetCornerTheme
import com.petcorner.petcorner.view.BottomNavigationBar
import com.petcorner.petcorner.viewmodel.AnimalViewModel
import com.petcorner.petcorner.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var animalViewModel: AnimalViewModel
        animalViewModel = ViewModelProvider(
            this
        ).get(AnimalViewModel::class.java)

        lateinit var profileViewModel: ProfileViewModel
        profileViewModel = ViewModelProvider(
            this
        ).get(ProfileViewModel::class.java)

        setContent {
            PetCornerTheme(){
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Animali",
                                    route = "animals",
                                    icon = Icons.Default.Favorite,
                                ),
                                BottomNavItem(
                                    name = "Utenti",
                                    route = "users",
                                    icon = Icons.Default.Person,
                                ),
                                BottomNavItem(
                                    name = "Profilo",
                                    route = "login",
                                    icon = Icons.Default.Settings,
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            },
                            viewModel = profileViewModel
                        )
                    }
                ) {
                    Navigation(navController = navController, animalViewModel, profileViewModel )
                }
            }
        }
    }
}


