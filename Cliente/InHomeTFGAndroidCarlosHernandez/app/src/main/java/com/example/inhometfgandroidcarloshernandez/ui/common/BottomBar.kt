package com.example.inhometfgandroidcarloshernandez.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.inhometfgandroidcarloshernandez.common.ConstantesError
import com.example.inhometfgandroidcarloshernandez.ui.framework.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun BottomBar(
    navController: NavController,
    screens: List<Screens>,
    onLogout: () -> Unit,
    isLoggedIn: Boolean,
    showSnackbar: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
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
                        navController.navigate(ConstantesPantallas.LOGIN) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    } else {
                        if (isLoggedIn) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        } else {
                            scope.launch {
                                showSnackbar(ConstantesError.NO_ESTA_LOGUEADO)
                            }
                        }
                    }
                }
            )
        }
    }
}