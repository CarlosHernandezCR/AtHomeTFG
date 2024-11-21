package com.example.inhometfgandroidcarloshernandez.ui.framework.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inhometfgandroidcarloshernandez.ui.common.BottomBar
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login.PortadaActivity
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val showSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomBar(navController = navController, screens = screensBottomBar) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Constantes.LOGIN
        ) {
            composable(Constantes.LOGIN) {
                PortadaActivity(
                    onNavigateLogin = {
                    },
                    showSnackbar = {
                        showSnackbar(it)
                    },
                    innerPadding = paddingValues
                )
            }
        }
    }
}