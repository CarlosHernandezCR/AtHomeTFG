package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.Cargando

@Composable
fun LoginActivity(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateLogin: (id: Int) -> Unit = {},
    showSnackbar: (String) -> Unit = {},
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(LoginContract.PortadaEvent.ErrorMostrado)
        }
    }
    LaunchedEffect(uiState.id) {
        uiState.id?.let { id ->
            onNavigateLogin(id)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        Portada(
            correo = uiState.correo,
            onCorreoChange = { viewModel.handleEvent(LoginContract.PortadaEvent.CorreoChange(it)) },
            login = { viewModel.handleEvent(LoginContract.PortadaEvent.Login) },
            modifier = Modifier.align(Alignment.Center),
            isLoading = uiState.isLoading
        )
    }
}

@Composable
fun Portada(
    correo: String,
    onCorreoChange: (String) -> Unit,
    login: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            isLoading -> Cargando()
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = correo,
                        onValueChange = onCorreoChange,
                        label = { Text(Constantes.CORREO) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick =  login,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(Constantes.INICIAR_SESION)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PortadaPreview() {
    Portada(
        correo = "carlos@ejemplo.com",
        onCorreoChange = {},
        login = {},
        isLoading = false
    )
}