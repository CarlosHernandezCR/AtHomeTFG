package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.ui.GlobalViewModel
import com.example.inhometfgandroidcarloshernandez.ui.common.ConstantesPantallas.nombresDias
import com.example.inhometfgandroidcarloshernandez.ui.common.ConstantesPantallas.nombresMeses
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioContract.DiaCalendario

@Composable
fun CalendarioActivity(
    globalViewModel: GlobalViewModel,
    showSnackbar: (String) -> Unit = {},
    viewModel: CalendarioViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect (globalViewModel.idCasa) {
        viewModel.handleEvent(CalendarioContract.CalendarioEvent.CargarEventos(globalViewModel.idCasa))
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(CalendarioContract.CalendarioEvent.ErrorMostrado)
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            CalendarScreen(
                anioActual = uiState.anioActual,
                mesActual = uiState.mesActual,
                cambioAnio = {
                    viewModel.handleEvent(
                        CalendarioContract.CalendarioEvent.CambiarAnio(
                            it
                        )
                    )
                },
                cambioMes = {
                    viewModel.handleEvent(
                        CalendarioContract.CalendarioEvent.CambiarMes(
                            it
                        )
                    )
                },
                diasDelMes = uiState.diasEnMes
            )
        }
    }
}

@Composable
fun CalendarScreen(
    anioActual: Int,
    mesActual: Int,
    cambioAnio: (Int) -> Unit,
    cambioMes: (Int) -> Unit,
    diasDelMes: List<List<DiaCalendario>>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { cambioAnio(anioActual - 1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Año anterior")
            }
            Text(
                text = anioActual.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            IconButton(onClick = { cambioAnio(anioActual + 1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Año siguiente")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { cambioMes(mesActual - 1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Mes anterior")
            }
            Text(
                text = nombresMeses[mesActual],
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            IconButton(onClick = { cambioMes(mesActual + 1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Mes siguiente")
            }
        }


        Spacer(modifier = Modifier.height(6.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color.Gray)
        Spacer(modifier = Modifier.height(6.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                nombresDias.forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            diasDelMes.forEach { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    week.forEach { day ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    Color(android.graphics.Color.parseColor(day.colorFondo))
                                )
                                .border(
                                    1.dp,
                                    Color.Gray,
                                    CircleShape
                                )
                                .clickable(enabled = day.colorFondo != "#C4C4C4") { day.onClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.numero.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

//            Spacer(modifier = Modifier.height(16.dp))
//
//        selectedEvent.let { event ->
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .background(Color.LightGray)
//                    .border(1.dp, Color.Gray)
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = "Título: ${event.titulo}",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//                Text(
//                    text = "Descripción: ${event.descripcion}",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//                Text(
//                    text = "Pedido por: ${event.organizador}",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//                Text(
//                    text = "Votación: ${event.votos}",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarScreen() {
    CalendarScreen(
        anioActual = 2024,
        mesActual = 11,
        cambioAnio = {},
        cambioMes = {},
        diasDelMes = generateDummyDays()
    )
}

fun generateDummyDays(): List<List<DiaCalendario>> {
    val diaCalendarios = mutableListOf<List<DiaCalendario>>()
    val currentMonthDays = 30
    val weekDiaCalendarios = mutableListOf<DiaCalendario>()

    for (i in 1..currentMonthDays) {
        val isToday = i == 14
        weekDiaCalendarios.add(DiaCalendario(numero = i, colorFondo = if (isToday) "#FF0000" else "#FFFFFF", otroMes = false) {})
        if (weekDiaCalendarios.size == 7) {
            diaCalendarios.add(weekDiaCalendarios.toList())
            weekDiaCalendarios.clear()
        }
    }

    if (weekDiaCalendarios.isNotEmpty()) {
        diaCalendarios.add(weekDiaCalendarios.toList())
    }

    return diaCalendarios
}
