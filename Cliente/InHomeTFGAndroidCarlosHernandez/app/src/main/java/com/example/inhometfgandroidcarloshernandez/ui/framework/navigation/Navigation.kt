package com.example.inhometfgandroidcarloshernandez.ui.framework.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inhometfgandroidcarloshernandez.ui.common.BottomBar
import com.example.inhometfgandroidcarloshernandez.ui.common.ConstantesPantallas
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados.EstadosActivity
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login.LoginActivity
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
            startDestination = ConstantesPantallas.LOGIN
        ) {
            composable(ConstantesPantallas.LOGIN) {
                LoginActivity(
                    onNavigateLogin = { id ->
                        navController.navigate("${ConstantesPantallas.PORTADA}/$id")
                    },
                    showSnackbar = {
                        showSnackbar(it)
                    },
                    innerPadding = paddingValues
                )
            }
            composable(
                route = "${ConstantesPantallas.PORTADA}${ConstantesPantallas.BARRA}${ConstantesPantallas.ID_VARIABLE}",
                arguments = listOf(navArgument(ConstantesPantallas.id) { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt(ConstantesPantallas.id) ?: 0
                EstadosActivity(
                    id = id,
                    innerPadding = paddingValues
                )
            }
        }
    }
}