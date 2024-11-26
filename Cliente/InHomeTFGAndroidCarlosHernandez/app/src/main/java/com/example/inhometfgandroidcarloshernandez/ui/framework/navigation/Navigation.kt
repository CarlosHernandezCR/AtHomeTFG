package com.example.inhometfgandroidcarloshernandez.ui.framework.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inhometfgandroidcarloshernandez.ui.GlobalViewModel
import com.example.inhometfgandroidcarloshernandez.ui.common.BottomBar
import com.example.inhometfgandroidcarloshernandez.ui.common.ConstantesPantallas
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioActivity
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados.EstadosActivity
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login.LoginActivity
import kotlinx.coroutines.launch

@Composable
fun Navigation(globalViewModel: GlobalViewModel = hiltViewModel()) {
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
                    onNavigateLogin = { idUsuario ->
                        globalViewModel.updateIdUsuario(idUsuario)
                        navController.navigate(ConstantesPantallas.CASA)
                    },
                    showSnackbar = showSnackbar,
                    innerPadding = paddingValues
                )
            }

            composable(ConstantesPantallas.CASA) {
                EstadosActivity(
                    idUsuario = globalViewModel.idUsuario,
                    innerPadding = paddingValues
                )
            }

            composable(ConstantesPantallas.CALENDARIO) {
                CalendarioActivity(
                    idUsuario = globalViewModel.idUsuario,
                    idCasa = globalViewModel.idCasa,
                    innerPadding = paddingValues)
            }
        }
    }
}
