package com.petcorner.petcorner

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.petcorner.petcorner.ui.theme.Navigation
import com.petcorner.petcorner.ui.theme.PetCornerTheme
import com.petcorner.petcorner.view.BottomNavigationBar

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetCornerTheme(){
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Animals",
                                    route = "animals",
                                    icon = Icons.Default.Star
                                ),
                                BottomNavItem(
                                    name = "Users",
                                    route = "users",
                                    icon = Icons.Default.Person,
                                ),
                                BottomNavItem(
                                    name = "Profile",
                                    route = "login",
                                    icon = Icons.Default.Home,
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}


