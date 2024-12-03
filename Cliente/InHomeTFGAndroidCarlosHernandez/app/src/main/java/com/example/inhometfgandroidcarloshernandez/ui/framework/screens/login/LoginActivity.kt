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

@Composable
fun LoginActivity(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateLogin: (id: Int) -> Unit = {},
    showSnackbar: (String) -> Unit = {},
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()
    val loginResult by viewModel.loginResult.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(LoginContract.PortadaEvent.ErrorMostrado)
        }
    }

    LaunchedEffect(loginResult) {
        loginResult?.let { id ->
            onNavigateLogin(id)
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        Portada(
            onCorreoChange = { correo ->
                viewModel.handleEvent(LoginContract.PortadaEvent.Login(correo))
            },
            isLoading = uiState.isLoading
        )
    }
}

@Composable
fun Portada(
    onCorreoChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    var correo by remember { mutableStateOf("carlos@ejemplo.com") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text(Constantes.CORREO) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onCorreoChange(correo) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Constantes.INICIAR_SESION)
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PortadaPreview() {
    Portada(
        onCorreoChange = {},
        isLoading = false
    )
}