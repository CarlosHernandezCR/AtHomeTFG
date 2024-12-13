package com.example.athometfgandroidcarloshernandez.ui.framework.screens.seleccionarcasa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.data.model.CasaDetallesDTO
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.calendario.Cargando

@Composable
fun SeleccionarCasaActivity(
    idUsuario: String,
    onCasaSelected: (idCasa: Int) -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: SeleccionarCasaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val mostrarCrearCasaDialog = remember { mutableStateOf(false) }
    val mostrarUnirseCasaDialog = remember { mutableStateOf(false) }

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
    if (mostrarCrearCasaDialog.value) {
        CrearCasaDialog(
            onDismiss = { mostrarCrearCasaDialog.value = false },
            onCrearCasa = { nombre, direccion, codigoPostal ->
                viewModel.handleEvent(SeleccionarCasaContract.SeleccionarCasaEvent.AgregarCasa(idUsuario,nombre, direccion, codigoPostal))
                mostrarCrearCasaDialog.value = false
            }
        )
    }

    if (mostrarUnirseCasaDialog.value) {
        UnirseCasaDialog(
            onDismiss = { mostrarUnirseCasaDialog.value = false },
            onUnirseCasa = { codigoInvitacion ->
                viewModel.handleEvent(SeleccionarCasaContract.SeleccionarCasaEvent.UnirseCasa(idUsuario,codigoInvitacion))
                mostrarUnirseCasaDialog.value = false
            }
        )
    }
    SeleccionarCasaScreen(
        isLoading = uiState.isLoading,
        casas = uiState.casas,
        onCasaSelected = onCasaSelected,
        onAddCasa = { mostrarCrearCasaDialog.value = true },
        onUnirseCasa = { mostrarUnirseCasaDialog.value = true }
    )
}



@Composable
fun SeleccionarCasaScreen(
    isLoading: Boolean,
    casas: List<CasaDetallesDTO>,
    onCasaSelected: (Int) -> Unit,
    onAddCasa: () -> Unit,
    onUnirseCasa: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
        ) {
            if (isLoading) {
                Cargando()
            } else {
                if (casas.isEmpty()) {
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
                            items(casas) { casa ->
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
        Botonera(
            onAddCasa = onAddCasa,
            onUnirseCasa = onUnirseCasa,
            modifier = Modifier.weight(0.4f)
        )
    }
}
@Composable
fun Botonera(
    onAddCasa: () -> Unit,
    onUnirseCasa: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onAddCasa) {
            Text(text = Constantes.CREAR_CASA)
        }
        Button(onClick = onUnirseCasa) {
            Text(text = Constantes.UNIRME_A_CASA)
        }
    }
}

@Composable
fun UnirseCasaDialog(
    onDismiss: () -> Unit,
    onUnirseCasa: (String) -> Unit
) {
    var codigoInvitacion by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = Constantes.UNIRME_A_CASA) },
        text = {
            Column {
                OutlinedTextField(
                    value = codigoInvitacion,
                    onValueChange = { codigoInvitacion = it },
                    label = { Text(Constantes.CODIGO_DE_INVITACION) }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onUnirseCasa(codigoInvitacion) }) {
                Text(Constantes.UNIRSE)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(Constantes.CANCELAR)
            }
        }
    )
}

@Composable
fun CrearCasaDialog(
    onDismiss: () -> Unit,
    onCrearCasa: (String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var codigoPostal by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = Constantes.CREAR_CASA) },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text(Constantes.NOMBRE) }
                )
                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text(Constantes.DIRECCION) }
                )
                OutlinedTextField(
                    value = codigoPostal,
                    onValueChange = { codigoPostal = it },
                    label = { Text(Constantes.CODIGO_POSTAL) }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onCrearCasa(nombre, direccion, codigoPostal) }) {
                Text(Constantes.CREAR_CASA)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(Constantes.CANCELAR)
            }
        }
    )
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

@Preview(showBackground = true)
@Composable
fun CasaPreview() {
    SeleccionarCasaScreen(
        isLoading = false,
        casas = listOf(
            CasaDetallesDTO(1, "Casa 1", "Dirección 1", "Código 1"),
            CasaDetallesDTO(2, "Casa 2", "Dirección 2", "Código 2")
        ),
        onCasaSelected = {},
        onAddCasa = {},
        onUnirseCasa = {}
    )
}
