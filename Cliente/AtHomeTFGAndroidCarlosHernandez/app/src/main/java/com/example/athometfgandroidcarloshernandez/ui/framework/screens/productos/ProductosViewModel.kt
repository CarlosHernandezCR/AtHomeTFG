package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athometfgandroidcarloshernandez.common.ConstantesError.ERROR_NUMERO_ENTERO
import com.example.athometfgandroidcarloshernandez.common.ConstantesError.NOMBRE_CANTIDAD_OBLIGATORIO
import com.example.athometfgandroidcarloshernandez.common.ConstantesError.PRODUCTO_EXISTENTE
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.athometfgandroidcarloshernandez.domain.usecases.productos.AgregarProductoUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.productos.CambiarCantidadUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.productos.CargarProductosUseCase
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos.ProductosContract.ProductosEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductosViewModel @Inject constructor(
    private val cambiarCantidadUseCase: CambiarCantidadUseCase,
    private val cargarProductosUseCase: CargarProductosUseCase,
    private val agregarProductoUseCase: AgregarProductoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductosContract.ProductosState())
    val uiState: StateFlow<ProductosContract.ProductosState> = _uiState.asStateFlow()

    fun handleEvent(event: ProductosEvent) {
        when (event) {
            is ProductosEvent.ErrorMostrado -> _uiState.update { it.copy(error = null) }
            is ProductosEvent.CargarProductos -> cargarProductos(event.idCajon, primeraCarga = true)
            is ProductosEvent.CambiarCantidad -> cambiarCantidad(event.idProducto, event.aumentar)
            is ProductosEvent.CambiarCajon -> cambiarCajon(event.idCajon)
            is ProductosEvent.CambiarMueble -> cambiarMueble(event.idMueble)
            is ProductosEvent.AgregarProducto -> agregarProducto(event.nombre, event.cantidad)
        }
    }

    private fun agregarProducto(nombre: String, cantidad: String) {
        if (nombre.isBlank() || cantidad.isBlank()) {
            _uiState.update { it.copy(error = NOMBRE_CANTIDAD_OBLIGATORIO) }
            return
        }else if (!cantidad.matches(Regex("\\d+"))) {
            _uiState.update { it.copy(error = ERROR_NUMERO_ENTERO) }
            return
        }else{
            val nuevaCantidad = cantidad.toInt()
            val productoExistente = _uiState.value.productos.find { it.nombre == nombre }
            if (productoExistente != null) {
                _uiState.update { it.copy(error = PRODUCTO_EXISTENTE) }
                return
            }
            viewModelScope.launch {
                agregarProductoUseCase.invoke(nombre, nuevaCantidad, _uiState.value.cajonActual).collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val productosActualizados = _uiState.value.productos + result.data!!
                            _uiState.update {
                                it.copy(
                                    productos = productosActualizados,
                                    isLoadingCantidad = false
                                )
                            }
                        }
                        is NetworkResult.Error -> _uiState.update { it.
                                copy(error = result.message, isLoadingCantidad = false) }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoadingCantidad = true) }
                    }
                }
            }
        }
    }

    private fun cambiarCajon(cajonId: String) {
        _uiState.update { it.copy(cajonActual = cajonId) }
        _uiState.value.muebleActual.let { cargarProductos(idCajon = cajonId) }
    }

    private fun cambiarMueble(muebleId: String) {
        _uiState.update { it.copy(muebleActual = muebleId) }
        _uiState.value.cajonActual.let { cargarProductos(idMueble = muebleId) }
    }

    private fun cargarProductos(idCajon: String? = null, idMueble: String? = null, primeraCarga: Boolean = false) {
        viewModelScope.launch {
            cargarProductosUseCase.invoke(idCajon, idMueble).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val muebles = result.data?.muebles ?: emptyList()
                        val cajones = result.data?.cajones ?: emptyList()
                        val productos = result.data?.productos ?: emptyList()
                        val muebleActual = if (primeraCarga) muebles.getOrNull(0)?.id ?: "" else idMueble ?: _uiState.value.muebleActual
                        val cajonActual = if (primeraCarga) cajones.getOrNull(0)?.id ?: "" else idCajon ?: _uiState.value.cajonActual
                        val idPropietario = result.data?.idPropietario ?: ""

                        _uiState.update { currentState ->
                            currentState.copy(
                                muebles = muebles,
                                cajones = cajones,
                                muebleActual = muebleActual,
                                cajonActual = cajonActual,
                                productos = productos,
                                idPropietario = idPropietario,
                                isLoading = false
                            )
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun cambiarCantidad(idProducto: String, aumentar: Boolean) {
        val producto = _uiState.value.productos.find { it.nombre == idProducto } ?: return
        val nuevaCantidad = if (aumentar) producto.unidades + 1 else maxOf(0, producto.unidades - 1)

        viewModelScope.launch {
            cambiarCantidadUseCase.invoke(idProducto, nuevaCantidad).collect { result ->
                when (result) {
                    is NetworkResult.Success -> actualizarLista(idProducto, nuevaCantidad)
                    is NetworkResult.Error -> _uiState.update {
                        it.copy(
                            error = result.message,
                            isLoadingCantidad = false
                        )
                    }

                    is NetworkResult.Loading -> _uiState.update { it.copy(isLoadingCantidad = true) }
                }
            }
        }
    }

    private fun actualizarLista(idProducto: String, nuevaCantidad: Int) {
        val productosActualizados = if (nuevaCantidad == 0) {
            _uiState.value.productos.filter { it.nombre != idProducto }
        } else {
            _uiState.value.productos.map { producto ->
                if (producto.nombre == idProducto) producto.copy(unidades = nuevaCantidad) else producto
            }
        }
        _uiState.update { it.copy(productos = productosActualizados, isLoadingCantidad = false) }
    }
}