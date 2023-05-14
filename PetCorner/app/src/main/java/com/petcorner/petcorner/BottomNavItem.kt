package com.petcorner.petcorner

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    var name:  String,
    var route: String,
    var icon: ImageVector,
    var badgeCount:  Int = 0
)
