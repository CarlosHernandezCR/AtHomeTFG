package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.util.Calendar

@Composable
fun CalendarioActivity(
    globalViewModel: GlobalViewModel,
    showSnackbar: (String) -> Unit = {},
    viewModel: CalendarioViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateEventos by viewModel.uiStateEventos.collectAsState()

    LaunchedEffect(globalViewModel.idCasa) {
        viewModel.handleEvent(CalendarioContract.CalendarioEvent.CargarEventos(globalViewModel.idCasa))
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(CalendarioContract.CalendarioEvent.ErrorMostrado)
        }

    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (uiState.isLoading) {
            item {
                CircularProgressIndicator(
                )
            }
        } else {
            item {
                Selector(
                    valorActual = uiState.anioActual,
                    opciones = (2024..2100).toList(),
                    onCambio = { nuevoAnio ->
                        viewModel.handleEvent(
                            CalendarioContract.CalendarioEvent.CambiarAnio(nuevoAnio)
                        )
                    })
            }
            item {

                Selector(
                    valorActual = uiState.mesActual,
                    opciones = nombresMeses.indices.toList(),
                    onCambio = { nuevoMes ->
                        viewModel.handleEvent(
                            CalendarioContract.CalendarioEvent.CambiarMes(nuevoMes)
                        )
                    },
                    mostrarOpciones = { nombresMeses[it] })
            }
            item {
                Calendario(
                    diasDelMes = uiState.diasEnMes,
                    onDayClick = { dia ->
                        viewModel.handleEvent(
                            CalendarioContract.CalendarioEvent.GetEventosDia(globalViewModel.idCasa,dia)
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Eventos del día",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(16.dp)
                            .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                DetallesEvento(
                    listaEventos = uiStateEventos.listaEventos
                )
            }
        }
    }
}

@Composable
fun DetallesEvento(
    listaEventos: List<CalendarioContract.EventoCasa>
) {
    if (listaEventos.isEmpty()) {
        Text(
            text = "No hay eventos para mostrar.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        return
    }

    var indiceSeleccionado by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
            .border(1.dp, Color.Gray)
    ) {
        Selector(
            valorActual = indiceSeleccionado,
            opciones = (1..listaEventos.size).toList(),
            onCambio = { nuevoIndice ->
                indiceSeleccionado = nuevoIndice
            },
            mostrarOpciones = { it.toString() })
        val eventoSeleccionado = listaEventos[indiceSeleccionado-1]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Título: ${eventoSeleccionado.tipo}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Descripción: ${eventoSeleccionado.nombre}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Pedido por: ${eventoSeleccionado.organizador}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Votación: ${eventoSeleccionado.votacion}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Hora comienzo: ${eventoSeleccionado.horaComienzo}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Hora finalización: ${eventoSeleccionado.horaFin}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun <T> Selector(
    valorActual: T,
    opciones: List<T>,
    onCambio: (T) -> Unit,
    mostrarOpciones: (T) -> String = { it.toString() }
) {
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
                }, enabled = opciones.indexOf(valorActual) > 0
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Anterior")
            }
            Text(
                text = mostrarOpciones(valorActual),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = {
                    val index = opciones.indexOf(valorActual)
                    if (index < opciones.size - 1) onCambio(opciones[index + 1])
                }, enabled = opciones.indexOf(valorActual) < opciones.size - 1
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
            }
        }
    }
}


@Composable
fun Calendario(
    diasDelMes: List<List<DiaCalendario>>,
    onDayClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
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
                                    1.dp, Color.Gray, CircleShape
                                )
                                .clickable(enabled = day.colorFondo != "#C4C4C4") { onDayClick(day.numero) },
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

@Preview(showBackground = true)
@Composable
fun PreviewCalendarScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Selector(valorActual = "2024", opciones = (2024..2100).toList(),
                onCambio = {})
        }
        item {

            Selector(valorActual = "Noviembre", opciones = (2024..2100).toList(),
                onCambio = {})
        }
        item {
            Calendario(
                diasDelMes = generateDummyDaysWithCompleteWeeks()
            )
        }
        item {
        DetallesEvento(
            listOf(
                CalendarioContract.EventoCasa(
                    "Reunión",
                    "Descripción 1",
                    "Juan",
                    "09:00",
                    "10:00",
                    "3/3"
                ),
                CalendarioContract.EventoCasa(
                    "Cumpleaños",
                    "Descripción 2",
                    "Ana",
                    "16:00",
                    "20:00",
                    "2/3"
                ),
                CalendarioContract.EventoCasa(
                    "Taller",
                    "Descripción 3",
                    "Luis",
                    "12:00",
                    "16:00",
                    "1/3"
                )
            )
        )}
    }
}

fun generateDummyDaysWithCompleteWeeks(): List<List<DiaCalendario>> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val primerDiaSemana = calendar.get(Calendar.DAY_OF_WEEK)
    val totalDiasEnMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val diasPrevios = if (primerDiaSemana == Calendar.SUNDAY) 6 else primerDiaSemana - 2

    calendar.add(Calendar.MONTH, -1)
    val totalDiasMesAnterior = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    calendar.add(Calendar.MONTH, 1)

    val COLOR_NORMAL = "#FFFFFF"
    val COLOR_HOY = "#0000ff"
    val COLOR_OTRO_MES = "#C4C4C4"

    val dias = mutableListOf<DiaCalendario>().apply {
        for (i in 0 until diasPrevios) {
            add(
                DiaCalendario(
                    numero = totalDiasMesAnterior - diasPrevios + i + 1,
                    colorFondo = COLOR_OTRO_MES,
                )
            )
        }
        for (i in 1..totalDiasEnMes) {
            val colorFondo = when {
                isToday(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), i) -> COLOR_HOY
                else -> COLOR_NORMAL
            }
            add(
                DiaCalendario(
                    numero = i,
                    colorFondo = colorFondo,
                )
            )
        }
        for (i in 1..(7 - size % 7)) {
            add(
                DiaCalendario(
                    numero = i,
                    colorFondo = COLOR_OTRO_MES,
                )
            )
        }
    }
    return dias.chunked(7)
}

private fun isToday(anio: Int, mes: Int, dia: Int): Boolean {
    val today = Calendar.getInstance()
    return anio == today.get(Calendar.YEAR) && mes == today.get(Calendar.MONTH) && dia == today.get(
        Calendar.DAY_OF_MONTH
    )
}


