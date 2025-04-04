package com.example.athometfgandroidcarloshernandez.ui.framework.screens.calendario

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.Constantes.FORMATO_HORA
import com.example.athometfgandroidcarloshernandez.ui.common.Cargando
import com.example.athometfgandroidcarloshernandez.ui.common.ConstantesPantallas.ANIO_ANTERIOR
import com.example.athometfgandroidcarloshernandez.ui.common.ConstantesPantallas.ANIO_SIGUIENTE
import com.example.athometfgandroidcarloshernandez.ui.common.ConstantesPantallas.nombresDias
import com.example.athometfgandroidcarloshernandez.ui.common.ConstantesPantallas.nombresMeses
import com.example.athometfgandroidcarloshernandez.ui.common.Selector
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioContract.DiaCalendario
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarioActivity(
    idUsuario: String,
    idCasa: String,
    showSnackbar: (String) -> Unit = {},
    viewModel: CalendarioViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateEventos by viewModel.uiStateEventos.collectAsState()

    LaunchedEffect(idCasa) {
        viewModel.handleEvent(CalendarioContract.CalendarioEvent.CargarEventos(idCasa))
    }

    LaunchedEffect(uiState.error, uiStateEventos.mensaje) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(CalendarioContract.CalendarioEvent.ErrorMostrado)
        }
        uiStateEventos.mensaje?.let {
            showSnackbar(it)
            viewModel.handleEvent(CalendarioContract.CalendarioEvent.ErrorMostrado)
        }
    }
    if (uiState.isLoading)
        Cargando()
    else
        CalendarioPantalla(
            anioActual = uiState.anioActual,
            mesActual = uiState.mesActual,
            diasEnMes = uiState.diasEnMes,
            diaSeleccionado = uiStateEventos.diaSeleccionado,
            listaEventos = uiStateEventos.listaEventos,
            idUsuario = idUsuario,
            mostrarDialogo = uiState.mostrarDialogo,
            loadingEvento = uiStateEventos.isLoading,
            onAnioCambio = { viewModel.handleEvent(CalendarioContract.CalendarioEvent.CambiarAnio(it)) },
            onCambioAnioPorMes = {
                viewModel.handleEvent(
                    CalendarioContract.CalendarioEvent.CambiarAnioPorMes(
                        it
                    )
                )
            },
            onMesCambio = { viewModel.handleEvent(CalendarioContract.CalendarioEvent.CambiarMes(it)) },
            onDiaClicado = {
                viewModel.handleEvent(
                    CalendarioContract.CalendarioEvent.CambioDiaSeleccionado(
                        it
                    )
                )
            },
            onCrearEvento = {
                viewModel.handleEvent(
                    CalendarioContract.CalendarioEvent.CrearEvento(
                        it
                    )
                )
            },
            onMostrarDialogoChange = { viewModel.handleEvent(CalendarioContract.CalendarioEvent.CambiarDialogo) },
            onVotar = { eventoId, idUsuarioVoto ->
                viewModel.handleEvent(CalendarioContract.CalendarioEvent.VotarEvento(eventoId, idUsuarioVoto))
            }
        )
}


@Composable
fun CalendarioPantalla(
    anioActual: Int,
    mesActual: Int,
    diasEnMes: List<List<DiaCalendario>>,
    idUsuario: String,
    diaSeleccionado: Int,
    listaEventos: List<CalendarioContract.EventoCasa>,
    mostrarDialogo: Boolean,
    loadingEvento: Boolean,
    onAnioCambio: (Int) -> Unit,
    onCambioAnioPorMes: (Boolean) -> Unit = {},
    onMesCambio: (Int) -> Unit,
    onDiaClicado: (Int) -> Unit,
    onCrearEvento: (CalendarioContract.EventoCasa) -> Unit,
    onMostrarDialogoChange: () -> Unit,
    onVotar: (String, String) -> Unit

) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        item {
            Selector(
                valorActual = anioActual,
                opciones = (2024..2100).toList(),
                onCambio = onAnioCambio
            )
        }
        item {
            Column {
                Selector(
                    valorActual = mesActual,
                    opciones = nombresMeses.indices.toList(),
                    onCambio = onMesCambio,
                    mostrarOpciones = { nombresMeses[it] }
                )
                if (mesActual == 0 || mesActual == 11) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (mesActual == 0) {
                            TextButton(onClick = { onCambioAnioPorMes(false) }) {
                                Text(ANIO_ANTERIOR)
                            }
                        }
                        if (mesActual == 11) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { onCambioAnioPorMes(true) }) {
                                    Text(ANIO_SIGUIENTE)
                                }
                            }
                        }
                    }
                }
            }
        }
        item {
            Calendario(
                diasDelMes = diasEnMes,
                diaClicado = onDiaClicado
            )
        }
        item {
            if (loadingEvento)
                Cargando()
            else
                CampoEvento(
                    diaSeleccionado = "$diaSeleccionado-${mesActual + 1}-$anioActual",
                    listaEventos = listaEventos,
                    idUsuario = idUsuario,
                    mostrarDialogo = mostrarDialogo,
                    onCrearEvento = onCrearEvento,
                    onMostrarDialogoChange = onMostrarDialogoChange,
                    onVotar = onVotar
                )
        }


    }
}

@Composable
fun CampoEvento(
    diaSeleccionado: String,
    idUsuario: String,
    listaEventos: List<CalendarioContract.EventoCasa>,
    mostrarDialogo: Boolean,
    onCrearEvento: (CalendarioContract.EventoCasa) -> Unit,
    onMostrarDialogoChange: () -> Unit,
    onVotar: (String, String) -> Unit
) {
    Spacer(modifier = Modifier.height(6.dp))
    if (listaEventos.isEmpty()) {
        Text(
            text = Constantes.NO_HAY_EVENTOS,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        DetallesEvento(listaEventos, idUsuario, onVotar, onMostrarDialogoChange)
    }
    if (!diaSeleccionado.startsWith(Constantes.MENOSUNO) && listaEventos.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            TextButton(onClick = onMostrarDialogoChange) {
                Text(Constantes.CREAR_EVENTO.uppercase(Locale.getDefault()))
            }
        }
    }

    if (mostrarDialogo) {
        CrearEventoDialog(
            fechaSeleccionada = diaSeleccionado,
            idUsuario = idUsuario,
            onDismiss = onMostrarDialogoChange,
            onCrearEvento = onCrearEvento
        )
    }
}
@Composable
fun DetallesEvento(
    listaEventos: List<CalendarioContract.EventoCasa>,
    idUsuario: String,
    onVotar: (String, String) -> Unit,
    onMostrarDialogoChange: () -> Unit
) {
    var indiceSeleccionado by remember { mutableIntStateOf(1) }
    val backgroundColor = MaterialTheme.colorScheme.surface
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(8.dp)
            .border(1.dp, borderColor)
    ) {
        Selector(
            valorActual = indiceSeleccionado,
            opciones = (1..listaEventos.size).toList(),
            onCambio = { nuevoIndice ->
                indiceSeleccionado = nuevoIndice
            },
            mostrarOpciones = { it.toString() })
        val eventoSeleccionado = listaEventos[indiceSeleccionado - 1]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = Constantes.TIPO + eventoSeleccionado.tipo,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = Constantes.RESERVA_DE + eventoSeleccionado.nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = Constantes.ORGANIZADOR + eventoSeleccionado.organizador,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    TextButton(onClick = onMostrarDialogoChange) {
                        Text(Constantes.CREAR_EVENTO.uppercase(Locale.getDefault()))
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = Constantes.HORA_INICIO + eventoSeleccionado.horaComienzo,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = Constantes.HORA_FIN + eventoSeleccionado.horaFin,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = Constantes.VOTACION + eventoSeleccionado.votacion,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    TextButton(onClick = { onVotar(eventoSeleccionado.id, idUsuario) }) {
                        Text(Constantes.VOTAR)
                    }
                }
            }
        }
    }
}


@Composable
fun Calendario(
    diasDelMes: List<List<DiaCalendario>>,
    diaClicado: (Int) -> Unit
) {
    var diaSeleccionado by remember { mutableIntStateOf(-1) }

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
                                    1.dp, if (day.numero == diaSeleccionado && day.colorFondo!= "#C4C4C4") Color.Green else Color.Gray, CircleShape
                                )
                                .clickable(enabled = day.colorFondo != "#C4C4C4") {
                                    diaSeleccionado = day.numero
                                    diaClicado(day.numero)
                                },
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


@SuppressLint("DefaultLocale")
@Composable
fun CrearEventoDialog(
    fechaSeleccionada: String,
    idUsuario: String,
    onDismiss: () -> Unit,
    onCrearEvento: (event: CalendarioContract.EventoCasa) -> Unit
) {
    var tipo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = Constantes.CREAR_EVENTO, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = Constantes.FECHA_SELECCIONADA + fechaSeleccionada)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = tipo,
                    onValueChange = { tipo = it },
                    label = { Text(Constantes.TIPO) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text(Constantes.RESERVA_DE) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                val context = LocalContext.current
                val calendar = Calendar.getInstance()

                val timePickerDialogInicio = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        horaInicio = String.format(FORMATO_HORA, hourOfDay, minute)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )

                val timePickerDialogFin = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        horaFin = String.format(FORMATO_HORA, hourOfDay, minute)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )

                TextButton(
                    onClick = { timePickerDialogInicio.show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = Constantes.HORA_INICIO + horaInicio)
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    onClick = { timePickerDialogFin.show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = Constantes.HORA_FIN + horaFin)
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(Constantes.SALIR)
                    }
                    TextButton(
                        onClick = {
                            val evento = CalendarioContract.EventoCasa(
                                tipo = tipo,
                                nombre = nombre,
                                organizador = idUsuario,
                                horaComienzo = horaInicio,
                                horaFin = horaFin,
                                votacion = "",
                                id = ""
                            )
                            onCrearEvento(evento)
                        }
                    ) {
                        Text(Constantes.CREAR_EVENTO)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarioScreen() {
    val diasEnMes = generarDiasDePrueba()
    val listaEventos = listOf(
        CalendarioContract.EventoCasa(
            tipo = "Reunión",
            nombre = "Descripción 1",
            organizador = "Juan",
            horaComienzo = "09:00",
            horaFin = "10:00",
            votacion = "3/3",
            id = "1"
        ),
        CalendarioContract.EventoCasa(
            tipo = "Cumpleaños",
            nombre = "Descripción 2",
            organizador = "Ana",
            horaComienzo = "16:00",
            horaFin = "20:00",
            votacion = "2/3",
            id = "2"
        ),
        CalendarioContract.EventoCasa(
            tipo = "Taller",
            nombre = "Descripción 3",
            organizador = "Luis",
            horaComienzo = "12:00",
            horaFin = "16:00",
            votacion = "1/3",
            id = "3"
        )
    )

    CalendarioPantalla(
        anioActual = 2024,
        mesActual = 0,
        diasEnMes = diasEnMes,
        diaSeleccionado = 15,
        listaEventos = listaEventos,
        mostrarDialogo = false,
        onAnioCambio = {},
        onMesCambio = {},
        onDiaClicado = {},
        onCrearEvento = {},
        idUsuario = "1",
        loadingEvento = false,
        onMostrarDialogoChange = {},
        onVotar = { _, _ -> }
    )
}

fun generarDiasDePrueba(): List<List<DiaCalendario>> {
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
                esHoy(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), i) -> COLOR_HOY
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

private fun esHoy(anio: Int, mes: Int, dia: Int): Boolean {
    val today = Calendar.getInstance()
    return anio == today.get(Calendar.YEAR) && mes == today.get(Calendar.MONTH) && dia == today.get(
        Calendar.DAY_OF_MONTH
    )
}


