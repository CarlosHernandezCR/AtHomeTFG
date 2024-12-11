package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.seleccionarcasa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.data.model.CasaDetallesDTO
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.Cargando

@Composable
fun SeleccionarCasaActivity(
    idUsuario: String,
    onCasaSelected: (idCasa: Int) -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: SeleccionarCasaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idUsuario) {
        idUsuario.let {
            viewModel.handleEvent(SeleccionarCasaContract.SeleccionarCasaEvent.CargarCasas(it))
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(SeleccionarCasaContract.SeleccionarCasaEvent.ErrorMostrado)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (uiState.isLoading) {
            Cargando()
        } else {
            if (uiState.casas.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = Constantes.SELECCIONAR_CASA,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = Constantes.NO_HAY_CASAS,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = Constantes.SELECCIONAR_CASA,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.casas) { casa ->
                            CasaItem(
                                casa = casa,
                                onCasaSelected = onCasaSelected
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CasaItem(casa: CasaDetallesDTO, onCasaSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCasaSelected(casa.id) },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = casa.nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = casa.direccion, style = MaterialTheme.typography.bodyMedium)
            Text(text = casa.codigo, style = MaterialTheme.typography.bodyMedium)
        }
    }
}