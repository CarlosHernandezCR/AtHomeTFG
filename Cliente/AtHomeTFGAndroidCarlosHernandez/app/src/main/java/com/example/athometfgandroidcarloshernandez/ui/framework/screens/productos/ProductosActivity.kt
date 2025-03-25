package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.Constantes.AUMENTAR
import com.example.athometfgandroidcarloshernandez.common.Constantes.DISMINUIR
import com.example.athometfgandroidcarloshernandez.common.Constantes.PEDIR_PRESTADO
import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.MuebleDTO
import com.example.athometfgandroidcarloshernandez.data.model.ProductoDTO
import com.example.athometfgandroidcarloshernandez.ui.common.Cargando
import com.example.athometfgandroidcarloshernandez.ui.common.Selector

@Composable
fun ProductosActivity(
    idCajon: String,
    idPropietario: String,
    idUsuario: String,
    viewModel: ProductosViewModel = hiltViewModel(),
    verCesta: (idUsuario: String) -> Unit = {},
    volver: () -> Unit = {},
    showSnackbar: (String) -> Unit = {},
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(ProductosContract.ProductosEvent.ErrorMostrado)
        }
    }
    LaunchedEffect(idCajon) {
        idCajon.let { idCajon ->
            viewModel.handleEvent(
                ProductosContract.ProductosEvent.CargarProductos(
                    idCajon,
                )
            )
        }
    }

    val esPropietario = idPropietario == idUsuario

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        PantallaProductos(
            muebleActual = uiState.muebleActual,
            muebles = uiState.muebles,
            cajonActual = uiState.cajonActual,
            cajones = uiState.cajones,
            productos = uiState.productos,
            productosCargando = uiState.productosCargando,
            cargando = uiState.isLoading,
            verCesta = { verCesta(idUsuario) },
            cambioMueble = {
                viewModel.handleEvent(
                    ProductosContract.ProductosEvent.CambiarMueble(
                        it
                    )
                )
            },
            cambioCajon = {
                viewModel.handleEvent(
                    ProductosContract.ProductosEvent.CambiarCajon(
                        it
                    )
                )
            },
            cambiarCantidad = { idProducto, aumentar ->
                viewModel.handleEvent(
                    ProductosContract.ProductosEvent.CambiarCantidad(idProducto, aumentar)
                )
            },
            agregarProducto = { nombre, cantidad ->
                viewModel.handleEvent(
                    ProductosContract.ProductosEvent.AgregarProducto(nombre, cantidad)
                )
            },
            volver = volver,
            esPropietario = esPropietario,
            pedirPrestado = { productoId ->
                // TODO
            }
        )
    }
}

@Composable
fun PantallaProductos(
    muebleActual: String,
    muebles: List<MuebleDTO>,
    cajonActual: String,
    cajones: List<CajonDTO>,
    productos: List<ProductoDTO>,
    productosCargando: Map<String, Boolean>,
    cargando: Boolean,
    cambioMueble: (String) -> Unit = {},
    cambioCajon: (String) -> Unit = {},
    cambiarCantidad: (String, Boolean) -> Unit,
    verCesta: () -> Unit,
    agregarProducto: (String, String) -> Unit,
    volver: () -> Unit,
    esPropietario: Boolean,
    pedirPrestado: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Selector(
            valorActual = muebles.find { it.id == muebleActual } ?: MuebleDTO(
                id = "",
                nombre = Constantes.NO_HAY_MUEBLE
            ),
            opciones = muebles,
            onCambio = { mueble -> cambioMueble(mueble.id) },
            mostrarOpciones = { it.nombre }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Selector(
            valorActual = cajones.find { it.id == cajonActual } ?: CajonDTO(
                id = "",
                nombre = Constantes.NO_HAY_CAJONES
            ),
            opciones = cajones,
            onCambio = { cajon -> cambioCajon(cajon.id) },
            mostrarOpciones = { it.nombre }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Cabecera()
        if (cargando) {
            Cargando()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                ListaProductos(
                    productos = productos,
                    cambiarCantidad = cambiarCantidad,
                    productosCargando = productosCargando,
                    esPropietario = esPropietario,
                    pedirPrestado = pedirPrestado
                )
            }
        }

        BotoneraProductos(
            verCesta = verCesta,
            agregarProducto = agregarProducto,
            volver = volver,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun ListaProductos(
    productos: List<ProductoDTO>,
    cambiarCantidad: (String, Boolean) -> Unit,
    productosCargando: Map<String, Boolean>,
    esPropietario: Boolean,
    pedirPrestado: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(productos) { producto ->
            ProductoItem(
                producto = producto,
                aumentar = { cambiarCantidad(producto.nombre, true) },
                disminuir = { cambiarCantidad(producto.nombre, false) },
                cargando = productosCargando[producto.nombre] == true,
                esPropietario = esPropietario,
                pedirPrestado = { pedirPrestado(producto.nombre) }
            )
        }
    }
}

@Composable
fun Cabecera() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = Constantes.IMAGEN,
            modifier = Modifier.weight(0.6f),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = Constantes.NOMBRE.uppercase(),
            modifier = Modifier.weight(2f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = Constantes.CANTIDAD,
            modifier = Modifier.weight(1.1f),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BotoneraProductos(
    verCesta: () -> Unit,
    agregarProducto: (String, String) -> Unit,
    volver: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AgregarProductoDialog(
            onDismiss = { showDialog = false },
            onConfirm = { nombre, cantidad ->
                agregarProducto(nombre, cantidad)
                showDialog = false
            }
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BotonAccion(texto = Constantes.VOLVER, accion = volver)
        BotonAccion(texto = Constantes.AGREGAR_PRODUCTO, accion = { showDialog = true })
        BotonAccion(texto = Constantes.CESTA, accion = verCesta)
    }
}

@Composable
fun AgregarProductoDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = Constantes.AGREGAR_PRODUCTO) },
        text = {
            Column {
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text(Constantes.NOMBRE) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text(Constantes.CANTIDAD) }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(nombre, cantidad) }) {
                Text(Constantes.AGREGAR)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(Constantes.VOLVER)
            }
        }
    )
}


@Composable
fun ProductoItem(
    producto: ProductoDTO,
    aumentar: () -> Unit,
    disminuir: () -> Unit,
    cargando: Boolean,
    esPropietario: Boolean,
    pedirPrestado: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.Center
                )

                if (cargando) {
                    Cargando()
                } else {
                    if (esPropietario) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = disminuir) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = DISMINUIR,
                                    tint = Color.Red
                                )
                            }

                            Text(text = "${producto.unidades}")

                            IconButton(onClick = aumentar) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = AUMENTAR,
                                    tint = Color.Blue
                                )
                            }
                        }
                    } else {
                        TextButton(onClick = pedirPrestado) {
                            Text(PEDIR_PRESTADO)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BotonAccion(texto: String, accion: () -> Unit) {
    Text(
        text = texto,
        modifier = Modifier
            .clickable { accion() }
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun PantallaProductosPreview() {
    PantallaProductos(
        muebleActual = "",
        muebles = listOf(MuebleDTO(id = "1", nombre = "Mueble 1")),
        cajonActual = "",
        cajones = listOf(CajonDTO(id = "1", nombre = "Cajon 1")),
        productos = listOf(
            ProductoDTO(nombre = "Producto 1", unidades = 1),
            ProductoDTO(nombre = "Producto 2", unidades = 2),
            ProductoDTO(nombre = "Producto 3", unidades = 3)
        ),
        cambioMueble = {},
        cambioCajon = {},
        productosCargando = emptyMap(),
        cambiarCantidad = { _, _ -> },
        verCesta = {},
        agregarProducto = { _, _ -> },
        cargando = false,
        volver = {},
        esPropietario = false,
        pedirPrestado = {}
    )
}