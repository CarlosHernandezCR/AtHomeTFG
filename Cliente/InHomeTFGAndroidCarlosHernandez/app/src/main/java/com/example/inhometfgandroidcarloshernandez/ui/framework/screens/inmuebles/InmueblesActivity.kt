package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.inhometfgandroidcarloshernandez.ui.GlobalViewModel
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.Selector

@Composable
fun InmueblesActivity(
    globalViewModel: GlobalViewModel,
    showSnackbar: (String) -> Unit = {},
    viewModel: InmueblesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(globalViewModel.idCasa) {
        viewModel.handleEvent(InmueblesContract.InmueblesEvent.CargarDatos(globalViewModel.idCasa))
    }
    InmueblesPantalla(
        habitacionActual = uiState.habitacionActual,
        habitaciones = uiState.habitaciones,
        muebleActual = uiState.muebleActual,
        muebles = uiState.muebles,
        cajones = uiState.cajones,
    )
}

@Composable
fun InmueblesPantalla(
    habitacionActual: String,
    habitaciones: List<String>,
    muebleActual: String,
    muebles: List<String>,
    cajones: List<CajonDTO>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Selector(
            valorActual = habitacionActual,
            opciones = habitaciones,
            onCambio = { },
            mostrarOpciones = { it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Selector(
            valorActual = muebleActual,
            opciones = muebles,
            onCambio = { },
            mostrarOpciones = { it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cajones) { item ->
                CajonItem(cajon = item.nombre, propietario = item.propietario)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {}) {
                Text(Constantes.AGREGAR_CAJON)
            }
            Button(onClick = {}) {
                Text(Constantes.AGREGAR_MUEBLE)
            }
        }
    }
}

@Composable
fun CajonItem(cajon: String, propietario: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = cajon, style = MaterialTheme.typography.bodyLarge)
            Text(text = propietario, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInmueblesActivity() {
    val habitaciones = listOf("Sala", "Cocina", "Dormitorio")
    val muebles = listOf("Armario", "Mesa", "Silla")
    val cajones = listOf(
        CajonDTO(nombre = "Cajón 1", propietario = "Carlos"),
        CajonDTO(nombre = "Cajón 2", propietario = "Ana"),
        CajonDTO(nombre = "Cajón 3", propietario = "Luis")
    )

    InmueblesPantalla(
        habitacionActual = "Sala",
        habitaciones = habitaciones,
        muebleActual = "Armario",
        muebles = muebles,
        cajones = cajones
    )
}