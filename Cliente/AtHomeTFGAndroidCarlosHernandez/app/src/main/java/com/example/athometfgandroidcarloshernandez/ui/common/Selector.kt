package com.example.athometfgandroidcarloshernandez.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.athometfgandroidcarloshernandez.common.Constantes

@Composable
fun <T> Selector(
    valorActual: T,
    opciones: List<T>,
    onCambio: (T) -> Unit,
    mostrarOpciones: (T) -> String = { it.toString() }
) {
    val isSingleOption = opciones.size == 1
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    val index = opciones.indexOf(valorActual)
                    if (index > 0) onCambio(opciones[index - 1])
                }, enabled = !isSingleOption && opciones.indexOf(valorActual) > 0
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = Constantes.ANTERIOR)
            }
            Text(
                text = mostrarOpciones(valorActual),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = {
                    val index = opciones.indexOf(valorActual)
                    if (index < opciones.size - 1) onCambio(opciones[index + 1])
                }, enabled = !isSingleOption && opciones.indexOf(valorActual) < opciones.size - 1
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = Constantes.SIGUIENTE
                )
            }
        }
    }
}