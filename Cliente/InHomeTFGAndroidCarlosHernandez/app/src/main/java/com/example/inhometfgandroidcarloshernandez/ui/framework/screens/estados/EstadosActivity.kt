package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.R
import com.example.inhometfgandroidcarloshernandez.data.model.response.UsuarioCasaResponseDTO
import com.example.inhometfgandroidcarloshernandez.ui.GlobalViewModel

@Composable
fun EstadosActivity(
    globalViewModel: GlobalViewModel,
    showSnackbar: (String) -> Unit = {},
    viewModel: EstadosViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateEstado by viewModel.uiStateEstado.collectAsState()

    LaunchedEffect(globalViewModel.idUsuario) {
        viewModel.handleEvent(EstadosContract.EstadosEvent.LoadCasa(globalViewModel.idUsuario))
    }

    LaunchedEffect(uiState.pantallaEstados) {
        if (uiState.pantallaEstados.idCasa != 0) {
            globalViewModel.updateIdCasa(uiState.pantallaEstados.idCasa)
        }
    }

    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let {
            showSnackbar(it)
            viewModel.handleEvent(EstadosContract.EstadosEvent.ErrorMostrado)
        }
    }
    LaunchedEffect(uiStateEstado.mensaje) {
        uiState.mensaje?.let {
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
                estadosDisponibles = uiState.pantallaEstados.estadosDisponibles,
                cambioEstado = { nuevoEstado ->
                    viewModel.handleEvent(
                        EstadosContract.EstadosEvent.CambiarEstado(nuevoEstado, globalViewModel.idUsuario)
                    )
                },
                cargandoEstado = uiStateEstado.isLoading,
            )
        }
    }
}

@Composable
fun PantallaEstados(
    titulo: String,
    direccion: String,
    usuariosCasa: List<UsuarioCasaResponseDTO>,
    estadoActual: String,
    estadosDisponibles: List<String>,
    cambioEstado: (String) -> Unit,
    cargandoEstado: Boolean,
) {
    var estadoSeleccionado by remember { mutableStateOf(estadoActual) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CasaInfo(titulo = titulo, direccion = direccion)
        Spacer(modifier = Modifier.height(16.dp))
        UsuariosList(
            usuariosCasa = usuariosCasa,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (cargandoEstado) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else{
            ComboBox(
                items = estadosDisponibles,
                selectedItem = estadoSeleccionado,
                onItemSelected = { nuevoEstado ->
                    if (estadoSeleccionado != nuevoEstado) {
                        estadoSeleccionado = nuevoEstado
                        cambioEstado(nuevoEstado)
                    }
                }
            )
        }
    }
}

@Composable
fun CasaInfo(titulo: String, direccion: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = direccion,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun UsuariosList(
    usuariosCasa: List<UsuarioCasaResponseDTO>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(usuariosCasa) { usuario ->
            UsuarioItem(nombre = usuario.nombre, estado = usuario.estado)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBox(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
) {
    val backgroundColor = when (selectedItem) {
        "En casa" -> Color.Green
        "Fuera de casa" -> Color.Red
        "Durmiendo" -> Color.Yellow
        else -> Color.Gray
    }
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)

    ) {
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text("Estado") },
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
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }

    }
}


@Composable
fun UsuarioItem(nombre: String, estado: String) {
    val backgroundColor = when (estado) {
        "En casa" -> Color.Green
        "Fuera de casa" -> Color.Red
        "Durmiendo" -> Color.Yellow
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fotoperfilandroid),
                contentDescription = "Usuario",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = nombre, color = Color.White)
                Text(text = estado, color = Color.White)
            }
        }
    }
}
