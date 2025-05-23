package com.example.athometfgandroidcarloshernandez.ui.framework.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.athometfgandroidcarloshernandez.ui.common.ConstantesPantallas


val screensBottomBar = listOf(
    Screens(ConstantesPantallas.LOGIN, Icons.AutoMirrored.Filled.ExitToApp),
    Screens(ConstantesPantallas.CALENDARIO, Icons.Filled.CalendarMonth),
    Screens(ConstantesPantallas.CASA, Icons.Filled.Home),
    Screens(ConstantesPantallas.INMUEBLES, Icons.Filled.Kitchen),
    Screens(ConstantesPantallas.CESTA, Icons.Filled.ShoppingCart)

)
data class Screens(val route: String, val icon: ImageVector)
