package com.example.athometfgandroidcarloshernandez.ui.framework.screens.estados

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athometfgandroidcarloshernandez.R
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.Constantes.ESTADO
import com.example.athometfgandroidcarloshernandez.common.Constantes.NUEVO_ESTADO
import com.example.athometfgandroidcarloshernandez.common.Constantes.SALIR
import com.example.athometfgandroidcarloshernandez.data.model.UsuarioCasaDTO
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.registro.ColorPickerDialog

@Composable
fun EstadosActivity(
    idUsuario: String,
    idCasa: String,
    showSnackbar: (String) -> Unit = {},
    viewModel: EstadosViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    volverSeleccionarCasa: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateEstado by viewModel.uiStateEstado.collectAsState()

    LaunchedEffect(idUsuario, idCasa) {
        idUsuario.let {
            idCasa.let {
                viewModel.handleEvent(EstadosContract.EstadosEvent.CargarCasa(idUsuario, idCasa))
            }
        }
    }

    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let {
            showSnackbar(it)
            viewModel.handleEvent(EstadosContract.EstadosEvent.ErrorMostrado)
        }
    }
    LaunchedEffect(uiStateEstado.mensaje) {
        uiStateEstado.mensaje?.let {
            showSnackbar(it)
            viewModel.handleEvent(EstadosContract.EstadosEvent.ErrorMostradoEstado)
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
            PantallaEstados(
                titulo = uiState.pantallaEstados.nombreCasa,
                direccion = uiState.pantallaEstados.direccion,
                usuariosCasa = uiState.pantallaEstados.usuariosCasa,
                estadoActual = uiState.pantallaEstados.estado,
                codigoInvitacion = uiState.pantallaEstados.codigoInvitacion,
                estadosDisponibles = uiState.pantallaEstados.estadosDisponibles,
                color = uiStateEstado.colorCambiarEstado,
                cargandoEstado = uiStateEstado.isLoading,
                cambioEstado = { nuevoEstado ->
                    viewModel.handleEvent(
                        EstadosContract.EstadosEvent.CambiarEstado(nuevoEstado, idCasa, idUsuario)
                    )
                },
                codigoCopiado = {
                    viewModel.handleEvent(EstadosContract.EstadosEvent.CodigoCopiado)
                },
                volverSeleccionarCasa = volverSeleccionarCasa,
                nuevoEstado = { nuevoEstado, color ->
                    viewModel.handleEvent(
                        EstadosContract.EstadosEvent.NuevoEstado(nuevoEstado, color, idUsuario)
                    )
                }
            )
        }
    }
}

@Composable
fun PantallaEstados(
    titulo: String,
    direccion: String,
    usuariosCasa: List<UsuarioCasaDTO>,
    estadoActual: String,
    codigoInvitacion: String,
    color: String,
    cargandoEstado: Boolean,
    estadosDisponibles: List<String>,
    cambioEstado: (String) -> Unit,
    codigoCopiado: (Unit) -> Unit,
    volverSeleccionarCasa: () -> Unit,
    nuevoEstado: (String, String) -> Unit
) {
    var estadoSeleccionado by remember { mutableStateOf(estadoActual) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CasaInfo(
            titulo = titulo,
            direccion = direccion,
            codigoInvitacion = codigoInvitacion,
            codigoCopiado = codigoCopiado,
            volverSeleccionarCasa = volverSeleccionarCasa
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (usuariosCasa.isEmpty())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = Constantes.SIN_USUARIOS)
            }
        else
            UsuariosList(
                usuariosCasa = usuariosCasa,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        Spacer(modifier = Modifier.height(16.dp))
        if (cargandoEstado) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            ComboBoxEstados(
                items = estadosDisponibles,
                selectedItem = estadoSeleccionado,
                titulo = Constantes.ESTADO,
                color = color,
                onItemSelected = { nuevoEstado ->
                    if (estadoSeleccionado != nuevoEstado) {
                        estadoSeleccionado = nuevoEstado
                        cambioEstado(nuevoEstado)
                    }
                },
                nuevoEstado = { nuevoEstado, color ->
                    nuevoEstado(nuevoEstado, color)
                }
            )
        }
    }
}

@Composable
fun CasaInfo(
    titulo: String, direccion: String, codigoInvitacion: String,
    codigoCopiado: (Unit) -> Unit,
    volverSeleccionarCasa: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = volverSeleccionarCasa) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = Constantes.VOLVER
                )
            }
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                clipboardManager.setText(AnnotatedString(codigoInvitacion))
                codigoCopiado(Unit)
            }) {
                Icon(
                    imageVector = Icons.Filled.ContentCopy,
                    contentDescription = Constantes.INVITACION_COPIAR,
                )
            }
        }
        Text(
            text = direccion,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun UsuariosList(
    usuariosCasa: List<UsuarioCasaDTO>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(usuariosCasa) { usuario ->
            UsuarioItem(
                nombre = usuario.nombre,
                estado = usuario.estado,
                colorEstado = usuario.colorEstado,
                colorUsuario = usuario.colorUsuario
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBoxEstados(
    items: List<String>,
    selectedItem: String,
    titulo: String,
    color: String,
    onItemSelected: (String) -> Unit,
    nuevoEstado: (String, String) -> Unit
) {
    val itemsWithNuevoEstado = items + NUEVO_ESTADO
    var expanded by remember { mutableStateOf(false) }
    var showNuevoEstadoDialog by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (color.isEmpty()) Color.Transparent else Color(color.toColorInt())
            )
    ) {
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text(titulo) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            itemsWithNuevoEstado.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        expanded = false
                        if (item == NUEVO_ESTADO) {
                            showNuevoEstadoDialog = true
                        } else {
                            onItemSelected(item)
                        }
                    }
                )
            }
        }
    }

    if (showNuevoEstadoDialog) {
        NuevoEstadoDialog(
            onDismiss = { showNuevoEstadoDialog = false },
            nuevoEstado = nuevoEstado
        )
    }
}

@Composable
fun UsuarioItem(nombre: String, estado: String, colorEstado: String, colorUsuario: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                8.dp,
                Color(colorUsuario.toColorInt()),
                RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                colorEstado.toColorInt()
            )
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fotoperfilandroid),
                contentDescription = Constantes.USUARIO_FOTO,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = nombre, color = Color.Black)
                Text(text = estado, color = Color.Black)
            }
        }
    }
}


@Composable
fun NuevoEstadoDialog(
    onDismiss: () -> Unit,
    nuevoEstado: (String, String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            var estado by remember { mutableStateOf("") }
            var showColorPicker by remember { mutableStateOf(false) }
            var selectedColor by remember { mutableStateOf(Color.White) }
            val onColorChange: (String) -> Unit = { color ->
                selectedColor = Color(color.toColorInt())
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = NUEVO_ESTADO, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = estado,
                    onValueChange = { estado = it },
                    label = { Text(ESTADO) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showColorPicker = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = selectedColor)
                ) {
                    Text(Constantes.SELECCIONAR_COLOR)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        nuevoEstado(estado, "#${Integer.toHexString(selectedColor.toArgb())}")
                        onDismiss()
                    }) {
                        Text(Constantes.CREAR_ESTADO)
                    }
                    Button(onClick = onDismiss) {
                        Text(SALIR)
                    }
                }
                if (showColorPicker) {
                    ColorPickerDialog(
                        onDismiss = { showColorPicker = false },
                        onColorSelected = { color ->
                            selectedColor = color
                            onColorChange("#${Integer.toHexString(color.toArgb())}")
                            showColorPicker = false
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewNuevoEstadoDialog() {
    NuevoEstadoDialog(onDismiss = {}, nuevoEstado = { _, _ -> })
}

@Preview(showBackground = true)
@Composable
fun PreviewEstadosActivity() {
    val usuariosCasa = listOf(
        UsuarioCasaDTO(
            nombre = "Carlos",
            estado = "En casa",
            colorEstado = "#FF0000",
            colorUsuario = "#FF0000"
        ),
        UsuarioCasaDTO(
            nombre = "Ana",
            estado = "Fuera de casa",
            colorEstado = "#00FF00",
            colorUsuario = "#0000FF"
        ),
        UsuarioCasaDTO(
            nombre = "Luis",
            estado = "Durmiendo",
            colorEstado = "#0000FF",
            colorUsuario = "#00FF00"
        )
    )
    val estadosDisponibles = listOf("En casa", "Fuera de casa", "Durmiendo")

    PantallaEstados(
        titulo = "Mi casita",
        direccion = "Calle Falsa 123",
        usuariosCasa = usuariosCasa,
        estadoActual = "En casa",
        codigoInvitacion = "ABC123",
        estadosDisponibles = estadosDisponibles,
        cambioEstado = {},
        codigoCopiado = {},
        cargandoEstado = false,
        color = "#FF0000",
        volverSeleccionarCasa = {},
        nuevoEstado = { _, _ -> }
    )
}