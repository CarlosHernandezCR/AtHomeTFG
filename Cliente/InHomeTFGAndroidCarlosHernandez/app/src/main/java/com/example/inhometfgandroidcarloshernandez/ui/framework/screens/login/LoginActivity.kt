package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.R
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.Cargando

@Composable
fun LoginActivity(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateLogin: (idUsuario: String) -> Unit = {},
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
    LaunchedEffect(uiState.idUsuario) {
        uiState.idUsuario?.let { idUsuario ->
            onNavigateLogin(idUsuario)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        Portada(
            identificador = uiState.identificador,
            password = uiState.password,
            onIdentificadorChange = { viewModel.handleEvent(LoginContract.PortadaEvent.IdentificadorChange(it)) },
            onPasswordChange = { viewModel.handleEvent(LoginContract.PortadaEvent.PasswordChange(it)) },
            login = { viewModel.handleEvent(LoginContract.PortadaEvent.Login) },
            modifier = Modifier.align(Alignment.Center),
            isLoading = uiState.isLoading
        )
    }
}

@Composable
fun Portada(
    identificador: String,
    password: String,
    onIdentificadorChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    login: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    val audioWide = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.audiowide)
    )
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
                    Text(
                        text = Constantes.NOMBRE_APP,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = audioWide,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TextField(
                        value = identificador,
                        onValueChange = onIdentificadorChange,
                        label = { Text(Constantes.IDENTIFICADOR) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = { Text(Constantes.CONTRASENA) },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = login,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(Constantes.INICIAR_SESION)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { /* TODO: Handle register action */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(Constantes.REGISTRARSE)
                        }
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
        identificador = "",
        password = "",
        onIdentificadorChange = {},
        onPasswordChange = {},
        login = {},
        isLoading = false
    )
}