package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.Constantes.AUMENTAR
import com.example.athometfgandroidcarloshernandez.common.Constantes.DISMINUIR
import com.example.athometfgandroidcarloshernandez.common.Constantes.ELEGIR_FOTO
import com.example.athometfgandroidcarloshernandez.common.Constantes.JPG
import com.example.athometfgandroidcarloshernandez.common.Constantes.PEDIR_PRESTADO
import com.example.athometfgandroidcarloshernandez.common.Constantes.QUITAR_FOTO
import com.example.athometfgandroidcarloshernandez.common.Constantes.TEMP_IMAGE_FILE_NAME
import com.example.athometfgandroidcarloshernandez.common.Constantes.TOMAR_FOTO
import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.MuebleDTO
import com.example.athometfgandroidcarloshernandez.ui.common.Cargando
import com.example.athometfgandroidcarloshernandez.ui.common.Selector
import java.io.File

@Composable
fun ProductosActivity(
    idCajon: String,
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
        if(!uiState.primerCargado) {
            idCajon.let { idCajon ->
                viewModel.handleEvent(
                    ProductosContract.ProductosEvent.CargarProductos(
                        idCajon,
                    )
                )
            }
            viewModel.handleEvent(ProductosContract.ProductosEvent.PrimerCargado)
        }
    }

    val esPropietario = uiState.idPropietario == idUsuario

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
            agregarProducto = { nombre, cantidad, imagen ->
                viewModel.handleEvent(
                    ProductosContract.ProductosEvent.AgregarProducto(nombre, cantidad, imagen)
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
    productos: List<ProductosContract.Producto>,
    productosCargando: Map<String, Boolean>,
    cargando: Boolean,
    cambioMueble: (String) -> Unit = {},
    cambioCajon: (String) -> Unit = {},
    cambiarCantidad: (Int, Boolean) -> Unit,
    verCesta: () -> Unit,
    agregarProducto: (String, String, String) -> Unit,
    volver: () -> Unit,
    esPropietario: Boolean,
    pedirPrestado: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (cargando) {
            Cargando()
        } else {
            Selector(
                valorActual = muebles.find { it.nombre == muebleActual } ?: MuebleDTO(
                    id = "",
                    nombre = Constantes.NO_HAY_MUEBLE
                ),
                opciones = muebles,
                onCambio = { mueble -> cambioMueble(mueble.id) },
                mostrarOpciones = { it.nombre }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Selector(
                valorActual = cajones.find { it.nombre == cajonActual } ?: CajonDTO(
                    id = "",
                    nombre = Constantes.NO_HAY_CAJONES
                ),
                opciones = cajones,
                onCambio = { cajon -> cambioCajon(cajon.id) },
                mostrarOpciones = { it.nombre }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Cabecera()
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
            esPropietario = esPropietario,
            verCesta = verCesta,
            agregarProducto = agregarProducto,
            volver = volver,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ListaProductos(
    productos: List<ProductosContract.Producto>,
    cambiarCantidad: (Int, Boolean) -> Unit,
    productosCargando: Map<String, Boolean>,
    esPropietario: Boolean,
    pedirPrestado: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (productos.isEmpty()) item {
            Text(
                text = Constantes.NO_HAY_PRODUCTOS,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        } else
            items(productos) { producto ->
                ProductoItem(
                    producto = producto,
                    aumentar = { cambiarCantidad(producto.id, true) },
                    disminuir = { cambiarCantidad(producto.id, false) },
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
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = Constantes.IMAGEN,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
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
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun BotoneraProductos(
    esPropietario: Boolean,
    verCesta: () -> Unit,
    agregarProducto: (String, String, String) -> Unit,
    volver: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AgregarProductoDialog(
            onDismiss = { showDialog = false },
            onConfirm = { nombre, cantidad, imagen ->
                agregarProducto(nombre, cantidad, imagen)
                showDialog = false
            }
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BotonAccion(texto = Constantes.VOLVER, accion = volver)
        if (esPropietario) BotonAccion(
            texto = Constantes.AGREGAR_PRODUCTO,
            accion = { showDialog = true })
        BotonAccion(texto = Constantes.CESTA, accion = verCesta)
    }
}



@Composable
fun AgregarProductoDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var imagenBytes by remember { mutableStateOf<ByteArray?>(null) }

    val cropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            val uri = result.uriContent
            uri?.let {
                val inputStream = context.contentResolver.openInputStream(it)
                imagenBytes = inputStream?.readBytes()
            }
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            cropLauncher.launch(
                CropImageContractOptions(
                    it,
                    CropImageOptions().apply {
                        aspectRatioX = 1
                        aspectRatioY = 1
                        fixAspectRatio = true
                        guidelines = CropImageView.Guidelines.ON
                    }
                )
            )
        }
    }

    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri.value?.let { uri ->
                cropLauncher.launch(
                    CropImageContractOptions(
                        uri,
                        CropImageOptions().apply {
                            aspectRatioX = 1
                            aspectRatioY = 1
                            fixAspectRatio = true
                            guidelines = CropImageView.Guidelines.ON
                        }
                    )
                )
            }
        }
    }

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
                    label = { Text(Constantes.CANTIDAD_MINUS) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (imagenBytes == null) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        ) {
                            Text(ELEGIR_FOTO)
                        }

                        Button(
                            onClick = {
                                val photoUri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    File.createTempFile(TEMP_IMAGE_FILE_NAME, JPG, context.cacheDir).apply {
                                        createNewFile()
                                        deleteOnExit()
                                    }
                                )
                                imageUri.value = photoUri
                                takePhotoLauncher.launch(photoUri)
                            }
                        ) {
                            Text(TOMAR_FOTO)
                        }
                    }
                } else {
                    imagenBytes?.let {
                        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        onClick = {
                            imagenBytes = null
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(QUITAR_FOTO)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val imagenBase64 = imagenBytes?.let { Base64.encodeToString(it, Base64.DEFAULT) } ?: ""
                onConfirm(nombre, cantidad, imagenBase64)
            }) {
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
    producto: ProductosContract.Producto,
    cargando: Boolean,
    esPropietario: Boolean,
    aumentar: () -> Unit,
    disminuir: () -> Unit,
    pedirPrestado: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 0.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (producto.imagen != null) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .weight(0.6f)
                ) {
                    Image(
                        bitmap = producto.imagen,
                        contentDescription = producto.nombre,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )

                }
            } else {
                Spacer(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                        .weight(0.6f)
                )
            }


            Text(
                text = producto.nombre,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 30.dp)
            )

            if (cargando) {
                Cargando()
            } else {
                if (esPropietario) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1.1f)
                            .padding(start = 7.dp)
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
                    TextButton(
                        onClick = pedirPrestado,
                        modifier = Modifier.weight(1.1f)
                    ) {
                        Text(PEDIR_PRESTADO)
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
            .padding(horizontal = 10.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun PantallaProductosPreview() {
    PantallaProductos(
        muebleActual = "Nevera",
        muebles = listOf(MuebleDTO(id = "1", nombre = "Mueble 1")),
        cajonActual = "Cajon1",
        cajones = listOf(CajonDTO(id = "1", nombre = "Cajon 1")),
        productos = listOf(
            ProductosContract.Producto(id = 0, nombre = "Producto 1", unidades = 1, imagen = null),
            ProductosContract.Producto(id = 0, nombre = "Producto 2", unidades = 2, imagen = null),
            ProductosContract.Producto(id = 0, nombre = "Producto 3", unidades = 3, imagen = null)
        ),
        cambioMueble = {},
        cambioCajon = {},
        productosCargando = emptyMap(),
        cambiarCantidad = { _, _ -> },
        verCesta = {},
        agregarProducto = { _, _, _ -> },
        cargando = false,
        volver = {},
        esPropietario = true,
        pedirPrestado = {},
    )
}