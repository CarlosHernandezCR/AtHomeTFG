package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.portada

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PortadaActivity(
    viewModel: PortadaViewModel = hiltViewModel(),
    onNavigateLogin: (String) -> Unit = {},
    showSnackbar: (String) -> Unit = {},
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(PortadaContract.PortadaEvent.ErrorMostrado)
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        Portada(
            estado = uiState.estado ?: "",
            onCorreoChange = { correo ->
                viewModel.handleEvent(PortadaContract.PortadaEvent.Login(correo))
            }
        )
    }
}

@Composable
fun Portada(
    estado: String,
    onCorreoChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var correo by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onCorreoChange(correo) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = estado)
    }
}

@Preview(showBackground = true)
@Composable
fun PortadaPreview() {
    Portada(
        estado = "DURMIENDO",
        onCorreoChange = {}
    )
}