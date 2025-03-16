package com.example.athometfgandroidcarloshernandez.ui.framework.navigation

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
import com.example.athometfgandroidcarloshernandez.data.remote.di.TokenManager
import com.example.athometfgandroidcarloshernandez.ui.common.BottomBar
import com.example.athometfgandroidcarloshernandez.ui.common.ConstantesPantallas
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioActivity
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.estados.EstadosActivity
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.inmuebles.InmueblesActivity
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.login.LoginActivity
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos.ProductosActivity
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.registro.RegistroActivity
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.seleccionarcasa.SeleccionarCasaActivity
import kotlinx.coroutines.launch

@Composable
fun Navigation(
    tokenManager: TokenManager
) {
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
        bottomBar = {
            BottomBar(
                navController = navController,
                screens = screensBottomBar,
                showSnackbar = showSnackbar,
                tokenManager = tokenManager
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ConstantesPantallas.LOGIN
        ) {
            composable(ConstantesPantallas.LOGIN) {
                LoginActivity(
                    onNavigateLogin = { idUsuario ->
                        navController.navigate("${ConstantesPantallas.SELECCIONAR_CASA}/$idUsuario")
                    },
                    onNavigateRegistro = {
                        navController.navigate(ConstantesPantallas.REGISTRO)
                    },
                    showSnackbar = showSnackbar,
                    innerPadding = paddingValues
                )
            }
            composable(ConstantesPantallas.REGISTRO) {
                RegistroActivity(
                    onRegisterSuccess = {
                        navController.navigate(ConstantesPantallas.LOGIN)
                    },
                    showSnackbar = showSnackbar,
                    innerPadding = paddingValues
                )
            }

            composable("${ConstantesPantallas.SELECCIONAR_CASA}/{idUsuario}") { backStackEntry ->
                val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: "0"
                SeleccionarCasaActivity(
                    idUsuario = idUsuario,
                    onCasaSelected = { idCasa ->
                        navController.navigate("${ConstantesPantallas.CASA}/$idUsuario/$idCasa")
                    },
                    showSnackbar = showSnackbar
                )
            }

            composable("${ConstantesPantallas.CASA}/{idUsuario}/{idCasa}") { backStackEntry ->
                val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: "0"
                val idCasa = backStackEntry.arguments?.getString("idCasa") ?: "0"
                EstadosActivity(
                    idUsuario = idUsuario,
                    idCasa = idCasa,
                    innerPadding = paddingValues,
                    showSnackbar = showSnackbar,
                    volverSeleccionarCasa = {
                        navController.navigate("${ConstantesPantallas.SELECCIONAR_CASA}/$idUsuario")
                    }
                )
            }

            composable("${ConstantesPantallas.CALENDARIO}/{idUsuario}/{idCasa}") { backStackEntry ->
                val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: "0"
                val idCasa = backStackEntry.arguments?.getString("idCasa") ?: "0"
                CalendarioActivity(
                    idUsuario = idUsuario,
                    idCasa = idCasa,
                    showSnackbar = showSnackbar
                )
            }

            composable("${ConstantesPantallas.INMUEBLES}/{idUsuario}/{idCasa}") { backStackEntry ->
                val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: "0"
                val idCasa = backStackEntry.arguments?.getString("idCasa") ?: "0"
                InmueblesActivity(
                    idUsuario = idUsuario,
                    idCasa = idCasa,
                    onCajonSeleccionado = { idCajon, idPropietario, idUsuario ->
                        navController.navigate("${ConstantesPantallas.PRODUCTOS}/$idCajon/$idPropietario/$idUsuario")
                    },
                    showSnackbar = showSnackbar
                )
            }
            composable("${ConstantesPantallas.PRODUCTOS}/{idCajon}/{idPropietario}/{idUsuario}") { backStackEntry ->
                val idCajon = backStackEntry.arguments?.getString("idCajon") ?: "0"
                val idPropietario = backStackEntry.arguments?.getString("idPropietario") ?: "0"
                val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: "0"
                ProductosActivity(
                    idCajon = idCajon,
                    idPropietario = idPropietario,
                    idUsuario = idUsuario,
                    verCesta = {
                        navController.navigate("${ConstantesPantallas.CESTA}/$idUsuario")
                    },
                    showSnackbar = showSnackbar,
                    innerPadding = paddingValues
                )
            }
            composable("${ConstantesPantallas.CESTA}/{idUsuario}") { backStackEntry ->
                val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: "0"
//                CestaActivity(
//                    idUsuario = idUsuario,
//                    showSnackbar = showSnackbar
//                )
            }
        }
    }
}
