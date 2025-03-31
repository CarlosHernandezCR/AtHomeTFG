package com.example.athometfgandroidcarloshernandez.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.athometfgandroidcarloshernandez.common.ConstantesError
import com.example.athometfgandroidcarloshernandez.data.remote.di.TokenManager
import com.example.athometfgandroidcarloshernandez.ui.framework.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun BottomBar(
    navController: NavController,
    screens: List<Screens>,
    showSnackbar: (String) -> Unit,
    tokenManager: TokenManager
) {
    val scope = rememberCoroutineScope()
    val onLogout: () -> Unit = {
        scope.launch {
            tokenManager.deleteAccessToken()
            tokenManager.deleteRefreshToken()
            navController.navigate(ConstantesPantallas.LOGIN) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    NavigationBar {
        val state = navController.currentBackStackEntryAsState()
        val currentDestination = state.value?.destination

        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.route) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    if (screen.route == ConstantesPantallas.LOGIN) {
                        onLogout()
                    } else {
                        scope.launch {
                            val idUsuario = tokenManager.obtenerIdUsuario()
                            val idCasa = tokenManager.obtenerIdCasa()
                            if (idUsuario == null) {
                                showSnackbar(ConstantesError.NO_ESTA_LOGUEADO)
                                return@launch
                            }
                            if (idCasa == null) {
                                showSnackbar(ConstantesError.CASA_NO_SELECCIONADA)
                                return@launch
                            }
                            val route = if (screen.route in listOf(
                                    ConstantesPantallas.CASA,
                                    ConstantesPantallas.CALENDARIO,
                                    ConstantesPantallas.INMUEBLES
                                )
                            ) {
                                "${screen.route}/$idUsuario/$idCasa"
                            } else {
                                "${screen.route}/$idUsuario"
                            }

                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    }
                }
            )
        }
    }
}