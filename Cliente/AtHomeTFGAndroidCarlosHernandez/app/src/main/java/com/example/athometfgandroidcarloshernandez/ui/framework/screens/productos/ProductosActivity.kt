package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athometfgandroidcarloshernandez.ui.common.Cargando

@Composable
fun ProductosActivity(
    idCajon: String,
    idPropietario: String,
    idUsuario: String,
    viewModel: ProductosViewModel = hiltViewModel(),
    verCesta: (idUsuario: String) -> Unit = {},
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
    LaunchedEffect(idCajon, idPropietario, idUsuario) {
        idUsuario.let { idUsuario ->
            idCajon.let { idCajon ->
                idPropietario.let { idPropietario ->
                    viewModel.handleEvent(
                        ProductosContract.ProductosEvent.CargarProductos(
                            idCajon,
                            idPropietario,
                            idUsuario
                        )
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        when{
            uiState.isLoading -> Cargando()
            else -> PantallaProductos(
                productos = uiState.productos,
                verCesta = verCesta
            )
        }
    }
}

@Composable
fun PantallaProductos(
    productos: List<Producto>,
    verCesta: (idUsuario: String) -> Unit
) {
}
@Preview(showBackground = true)
@Composable
fun PortadaPreview() {

}