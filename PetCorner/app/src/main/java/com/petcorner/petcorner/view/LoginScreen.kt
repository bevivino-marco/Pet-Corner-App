package com.petcorner.petcorner.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.petcorner.petcorner.viewmodel.ProfileViewModel

@Composable
fun LoginScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Login(navController,profileViewModel)
    }
}