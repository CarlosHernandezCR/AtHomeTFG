package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import androidx.lifecycle.ViewModel
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos.ProductosContract.ProductosEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ProductosViewModel @Inject constructor(
    //val cambiarCantidadUseCase: CambiarCantidadUseCase
    ): ViewModel(){
    private val _uiState = MutableStateFlow(ProductosContract.ProductosState())
    val uiState: StateFlow<ProductosContract.ProductosState> = _uiState.asStateFlow()

    fun handleEvent(event: ProductosEvent) {
        when (event) {
            is ProductosEvent.ErrorMostrado -> _uiState.update { it.copy(error = null)}
            is ProductosEvent.CargarProductos -> cargarProductos(event.idCajon, event.idPropietario, event.idUsuario)
            is ProductosEvent.CambiarCantidad -> cambiarCantidad(event.idProducto, event.aumentar)
            is ProductosEvent.CambiarCajon -> TODO()
            is ProductosEvent.CambiarMueble -> TODO()
        }
    }

    private fun cargarProductos(idCajon: String, idPropietario: String, idUsuario: String) {
    }
    private fun cambiarCantidad(idProducto: String, aumentar: Boolean) {
        val producto = _uiState.value.productos.find { it.nombre == idProducto } ?: return
        val nuevaCantidad = if (aumentar) producto.unidades + 1 else maxOf(0, producto.unidades - 1)

//        viewModelScope.launch {
//            cambiarCantidadUseCase.invoke(idProducto, nuevaCantidad).collect { result ->
//                when (result) {
//                    is NetworkResult.Success -> actualizarLista(idProducto, nuevaCantidad)
//                    is NetworkResult.Error -> _uiState.update { it.copy(error = result.message, isLoading = false) }
//                    is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
//                }
//            }
//        }
    }

    private fun actualizarLista(idProducto: String, nuevaCantidad: Int) {
        val productosActualizados = if (nuevaCantidad == 0) {
            _uiState.value.productos.filter { it.nombre != idProducto }
        } else {
            _uiState.value.productos.map { producto ->
                if (producto.nombre == idProducto) producto.copy(unidades = nuevaCantidad) else producto
            }
        }
        _uiState.update { it.copy(productos = productosActualizados, isLoading = false) }
    }

}

