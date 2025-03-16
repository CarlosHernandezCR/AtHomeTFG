package com.example.athometfgandroidcarloshernandez.ui.framework.screens.seleccionarcasa

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.Constantes.ESTA_SEGURO_DE_SALIR_DE_ESTA_CASA_
import com.example.athometfgandroidcarloshernandez.common.Constantes.SALIR
import com.example.athometfgandroidcarloshernandez.data.model.CasaDetallesDTO
import com.example.athometfgandroidcarloshernandez.ui.common.Cargando
import com.example.athometfgandroidcarloshernandez.ui.common.ConfirmationDialog

@Composable
fun SeleccionarCasaActivity(
    idUsuario: String,
    onCasaSelected: (idCasa: String) -> Unit,
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
                viewModel.handleEvent(
                    SeleccionarCasaContract.SeleccionarCasaEvent.AgregarCasa(
                        idUsuario,
                        nombre,
                        direccion,
                        codigoPostal
                    )
                )
                mostrarCrearCasaDialog.value = false
            }
        )
    }

    if (mostrarUnirseCasaDialog.value) {
        UnirseCasaDialog(
            onDismiss = { mostrarUnirseCasaDialog.value = false },
            onUnirseCasa = { codigoInvitacion ->
                viewModel.handleEvent(
                    SeleccionarCasaContract.SeleccionarCasaEvent.UnirseCasa(
                        idUsuario,
                        codigoInvitacion
                    )
                )
                mostrarUnirseCasaDialog.value = false
            }
        )
    }
    SeleccionarCasaScreen(
        isLoading = uiState.isLoading,
        casas = uiState.casas,
        onCasaSelected = onCasaSelected,
        onAddCasa = { mostrarCrearCasaDialog.value = true },
        onUnirseCasa = { mostrarUnirseCasaDialog.value = true },
        salirCasa = { idCasa ->
            viewModel.handleEvent(
                SeleccionarCasaContract.SeleccionarCasaEvent.SalirCasa(
                    idUsuario,
                    idCasa
                )
            )
        }
    )
}


@Composable
fun SeleccionarCasaScreen(
    isLoading: Boolean,
    casas: List<CasaDetallesDTO>,
    onCasaSelected: (String) -> Unit,
    onAddCasa: () -> Unit,
    onUnirseCasa: () -> Unit,
    salirCasa: (String) -> Unit
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
                                    onCasaSelected = onCasaSelected,
                                    salirCasa = salirCasa
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
) {
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
                Text(SALIR)
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
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(SALIR)
            }
        },
        confirmButton = {
            Button(onClick = { onCrearCasa(nombre, direccion, codigoPostal) }) {
                Text(Constantes.CREAR_CASA)
            }
        },
    )
}

@Composable
fun CasaItem(
    casa: CasaDetallesDTO,
    onCasaSelected: (String) -> Unit,
    salirCasa: (String) -> Unit
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    if (mostrarDialogo) {
        ConfirmationDialog(
            text = ESTA_SEGURO_DE_SALIR_DE_ESTA_CASA_,
            onDismiss = { mostrarDialogo = false },
            onConfirm = {
                salirCasa(casa.id)
                mostrarDialogo = false
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
            .background(Color.LightGray, RoundedCornerShape(8.dp)),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .clickable { onCasaSelected(casa.id) }) {
                Text(text = casa.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = casa.direccion, style = MaterialTheme.typography.bodyMedium)
                Text(text = casa.codigo, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { mostrarDialogo = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = Constantes.SALIR_DE_CASA,
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CasaPreview() {
    SeleccionarCasaScreen(
        isLoading = false,
        casas = listOf(
            CasaDetallesDTO("1", "Casa 1", "Dirección 1", "Código 1"),
            CasaDetallesDTO("2", "Casa 2", "Dirección 2", "Código 2")
        ),
        onCasaSelected = {},
        onAddCasa = {},
        onUnirseCasa = {},
        salirCasa = {}
    )
}
