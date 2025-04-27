package com.example.athometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.Constantes.CONFIRMAR_BORRAR_CAJON
import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.HabitacionDTO
import com.example.athometfgandroidcarloshernandez.data.model.MuebleDTO
import com.example.athometfgandroidcarloshernandez.ui.common.Cargando
import com.example.athometfgandroidcarloshernandez.ui.common.ConfirmationDialog
import com.example.athometfgandroidcarloshernandez.ui.common.Selector
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.inmuebles.InmueblesContract.UsuarioInmuebles
import kotlin.math.roundToInt

@Composable
fun InmueblesActivity(
    idUsuario: String,
    idCasa: String,
    onCajonSeleccionado: (String) -> Unit,
    showSnackbar: (String) -> Unit = {},
    viewModel: InmueblesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(idCasa) {
        viewModel.handleEvent(InmueblesContract.InmueblesEvent.CargarDatos(idCasa))
        viewModel.handleEvent(InmueblesContract.InmueblesEvent.CargarUsuarios(idCasa))
    }
    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let { showSnackbar.invoke(it) }
        viewModel.handleEvent(InmueblesContract.InmueblesEvent.MensajeMostrado)
    }
    if (uiState.isLoading)
        Cargando()
    else {
        InmueblesPantalla(
            habitacionActual = uiState.idHabitacionActual,
            habitaciones = uiState.habitaciones,
            muebleActual = uiState.idMuebleActual,
            muebles = uiState.muebles,
            cajones = uiState.cajones,
            usuarios = uiState.usuarios,
            cargandoCajones = uiState.loadingCajones,
            cambioHabitacion = {
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.CambioHabitacion(
                        it
                    )
                )
            },
            cambioMueble = { viewModel.handleEvent(InmueblesContract.InmueblesEvent.CambioMueble(it)) },
            borrarCajon = { idCajon, idPropietario ->
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.BorrarCajon(
                        idCajon,
                        idUsuario,
                        idPropietario
                    )
                )
            },
            agregarCajon = { nombre, idPropietario ->
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.AgregarCajon(
                        nombre,
                        idPropietario
                    )
                )
            },
            agregarMueble = { nombre ->
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.AgregarMueble(
                        nombre
                    )
                )
            },
            agregarHabitacion = { nombre ->
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.AgregarHabitacion(
                        nombre
                    )
                )
            },
            onCajonSeleccionado = {
                if(it != "") onCajonSeleccionado(it)
            },
        )
    }
}

@Composable
fun InmueblesPantalla(
    habitacionActual: String,
    habitaciones: List<HabitacionDTO>,
    muebleActual: String,
    muebles: List<MuebleDTO>,
    cajones: List<CajonDTO>,
    usuarios: List<UsuarioInmuebles> = emptyList(),
    cargandoCajones: Boolean,
    cambioHabitacion: (String) -> Unit = {},
    cambioMueble: (String) -> Unit = {},
    borrarCajon: (String, String) -> Unit = { _, _ -> },
    agregarCajon: (String, String) -> Unit,
    agregarMueble: (String) -> Unit = {},
    agregarHabitacion: (String) -> Unit = {},
    onCajonSeleccionado: (String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var isCajon by remember { mutableStateOf(false) }
    val areHabitacionesEmpty = habitaciones.isEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Selector(
            valorActual = habitaciones.find { it.id == habitacionActual } ?: HabitacionDTO(
                id = "",
                nombre = Constantes.ANADE_HABITACION
            ),
            opciones = habitaciones,
            onCambio = { habitacion ->
                cambioHabitacion(habitacion.id)
            },
            mostrarOpciones = { it.nombre }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Selector(
            valorActual = muebles.find { it.id == muebleActual } ?: MuebleDTO(
                id = "",
                nombre = Constantes.NO_HAY_MUEBLE
            ),
            opciones = muebles,
            onCambio = { mueble ->
                cambioMueble(mueble.id)
            },
            mostrarOpciones = { it.nombre }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.weight(2f)
            ) {
                when {
                    cargandoCajones -> {
                        item {
                            Cargando()
                        }
                    }

                    cajones.isEmpty() -> {
                        item {
                            Text(
                                text = Constantes.NO_HAY_CAJONES,
                                modifier = Modifier.padding(16.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    else -> {
                        items(cajones) { item ->
                            SwipeToDeleteItem(
                                cajon = item,
                                borrarCajon = borrarCajon,
                                onCajonSeleccionado = onCajonSeleccionado
                            )
                        }
                    }
                }
            }

            BotoneraMuebles(
                showDialog = { showDialog = it },
                dialogTitle = { dialogTitle = it },
                isCajon = { isCajon = it },
                areHabitacionesEmpty = areHabitacionesEmpty,
                modifier = Modifier
                    .weight(0.6f),
            )
        }

        if (showDialog) {
            DialogNuevoElemento(
                title = dialogTitle,
                usuarios = usuarios,
                onConfirm = { name, idPropietario ->
                    if (isCajon) {
                        if (idPropietario != null) {
                            agregarCajon(name, idPropietario)
                        }
                    } else {
                        if (dialogTitle == Constantes.AGREGAR_HABITACION) {
                            agregarHabitacion(name)
                        } else {
                            agregarMueble(name)
                        }
                    }
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun DialogNuevoElemento(
    title: String,
    usuarios: List<UsuarioInmuebles> = emptyList(),
    onConfirm: (String, String?) -> Unit,
    onDismiss: () -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var selectedUsuario by remember { mutableStateOf<UsuarioInmuebles?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text(Constantes.NOMBRE) },
                    modifier = Modifier.fillMaxWidth()
                )
                if (usuarios.isNotEmpty() && title == Constantes.AGREGAR_CAJON) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ComboBoxPropietarios(
                        items = usuarios,
                        selectedItem = selectedUsuario?.nombre ?: "",
                        titulo = Constantes.PROPIETARIO,
                        onItemSelected = { name ->
                            selectedUsuario = usuarios.find { it.nombre == name }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(Constantes.SALIR)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onConfirm(itemName, selectedUsuario?.id) }) {
                        Text(Constantes.AGREGAR)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeToDeleteItem(
    cajon: CajonDTO,
    borrarCajon: (String, String) -> Unit,
    onCajonSeleccionado: (String) -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val swipeThreshold = 300f
    val anchors = mapOf(0f to 0, -swipeThreshold to 1)
    var mostrarDialogo by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CajonItem(
                    cajon = cajon,
                    propietario = cajon.propietario,
                    onCajonSeleccionado = onCajonSeleccionado
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = Constantes.BORRAR,
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        if (swipeableState.currentValue == 1) {
            LaunchedEffect(Unit) {
                mostrarDialogo = true
            }
        }
    }
    if (mostrarDialogo) {
        ConfirmationDialog(
            text = CONFIRMAR_BORRAR_CAJON,
            onDismiss = { mostrarDialogo = false },
            onConfirm = {
                borrarCajon(cajon.id, cajon.idPropietario)
                mostrarDialogo = false
            }
        )
    }
}

@Composable
fun BotoneraMuebles(
    modifier: Modifier = Modifier,
    showDialog: (Boolean) -> Unit,
    dialogTitle: (String) -> Unit,
    isCajon: (Boolean) -> Unit,
    areHabitacionesEmpty: Boolean = false,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Button(
            onClick = {
                dialogTitle(Constantes.AGREGAR_HABITACION)
                isCajon(false)
                showDialog(true)
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 6.dp)
        ) {
            Text(
                text = Constantes.AGREGAR_HABITACION,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            onClick = {
                dialogTitle(Constantes.AGREGAR_MUEBLE)
                isCajon(false)
                showDialog(true)
            },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 6.dp),
            enabled = !areHabitacionesEmpty
        ) {
            Text(
                text = Constantes.AGREGAR_MUEBLE,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            onClick = {
                dialogTitle(Constantes.AGREGAR_CAJON)
                isCajon(true)
                showDialog(true)
            },
            modifier = Modifier
                .weight(1f)
                .padding(start = 6.dp),
            enabled = !areHabitacionesEmpty
        ) {
            Text(
                text = Constantes.AGREGAR_CAJON,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun CajonItem(
    cajon: CajonDTO,
    propietario: String,
    onCajonSeleccionado: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCajonSeleccionado(cajon.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = cajon.nombre, style = MaterialTheme.typography.titleMedium)
            Text(
                text = propietario,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBoxPropietarios(
    items: List<UsuarioInmuebles>,
    selectedItem: String,
    titulo: String,
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var colorSelected by remember { mutableStateOf("#000000") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .border(width = 2.dp, color = Color(colorSelected.toColorInt()))
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
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.nombre) },
                    onClick = {
                        expanded = false
                        onItemSelected(item.nombre)
                        colorSelected = item.color
                    },
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = Color(item.color.toColorInt())
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialogNuevoElemento() {
    val usuarios = listOf(
        UsuarioInmuebles(id = "1", nombre = "Carlos", color = Constantes.VERDE),
        UsuarioInmuebles(id = "2", nombre = "Ana", color = Constantes.AMARILLO),
        UsuarioInmuebles(id = "3", nombre = "Luis", color = Constantes.ROJO)
    )
    DialogNuevoElemento(
        title = Constantes.AGREGAR_CAJON,
        usuarios,
        onConfirm = { _, _ -> },
        onDismiss = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewInmueblesActivity() {
    val habitaciones = listOf(
        HabitacionDTO(id = "1", nombre = "Habitación 1"),
        HabitacionDTO(id = "2", nombre = "Habitación 2"),
        HabitacionDTO(id = "3", nombre = "Habitación 3")
    )
    val muebles =
        listOf(MuebleDTO(id = "1", nombre = "Mesa"), MuebleDTO(id = "2", nombre = "Silla"))
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
        cajones = cajones,
        cargandoCajones = false,
        cambioHabitacion = {},
        cambioMueble = {},
        agregarCajon = { _, _ -> },
        agregarMueble = {},
        agregarHabitacion = {},
        onCajonSeleccionado = { _ -> },
    )
}