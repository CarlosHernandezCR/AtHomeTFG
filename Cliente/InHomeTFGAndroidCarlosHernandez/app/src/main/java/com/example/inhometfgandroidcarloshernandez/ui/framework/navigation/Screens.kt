package com.example.inhometfgandroidcarloshernandez.ui.framework.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.ui.graphics.vector.ImageVector

val screensBottomBar = listOf(
    Screens("", Icons.AutoMirrored.Filled.ExitToApp),
    Screens("", Icons.AutoMirrored.Filled.ArrowBack),
    Screens("", Icons.AutoMirrored.Filled.ArrowBack),
    Screens("", Icons.AutoMirrored.Filled.ArrowBack),

)
data class Screens(val route: String, val icon: ImageVector)
