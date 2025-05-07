package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.ConstantesError.ERROR_NUMERO_ENTERO
import com.example.athometfgandroidcarloshernandez.common.ConstantesError.IMAGEN_OBLIGATORIA
import com.example.athometfgandroidcarloshernandez.common.ConstantesError.NOMBRE_CANTIDAD_OBLIGATORIO
import com.example.athometfgandroidcarloshernandez.common.ConstantesError.PRODUCTO_EXISTENTE
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.athometfgandroidcarloshernandez.domain.usecases.productos.AgregarCajonUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.productos.AgregarProductoUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.productos.CambiarCantidadUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.productos.CargarProductosUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.productos.PedirPrestadoUseCase
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
    private val agregarProductoUseCase: AgregarProductoUseCase,
    private val agregarCajonUseCase: AgregarCajonUseCase,
    private val pedirPrestadoUseCase: PedirPrestadoUseCase,
    val imageLoader: ImageLoader
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
            is ProductosEvent.AgregarProducto -> agregarProducto(
                event.nombre,
                event.cantidad,
                event.imagen
            )

            ProductosEvent.PrimerCargado -> _uiState.update { it.copy(primerCargado = true) }
            is ProductosEvent.AgregarCajon -> agregarCajon(event.nombre, event.idUsuario)
            is ProductosEvent.PedirPrestado -> pedirPrestado(event.productoId, event.idUsuario)
        }
    }

    private fun pedirPrestado(productoId: String, idUsuario: String) {
        viewModelScope.launch {
            pedirPrestadoUseCase.invoke(productoId, idUsuario).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(error = Constantes.PEDIDO_REALIZADO + result.data!!.nombreUsuario) }
                    }
                    is NetworkResult.Error -> _uiState.update {
                        it.copy(
                            error = result.message,
                        )
                    }
                    is NetworkResult.Loading -> _uiState.update { it.copy(error = Constantes.SOLICITANDO_PEDIDO) }
                }
            }
        }
    }

    private fun agregarCajon(nombre: String, idUsuario: String) {
        if (nombre.isBlank()) {
            _uiState.update { it.copy(error = NOMBRE_CANTIDAD_OBLIGATORIO) }
            return
        } else {
            viewModelScope.launch {
                agregarCajonUseCase.invoke(uiState.value.muebleActual, nombre, idUsuario)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Success -> {
                                val cajonesActualizados =
                                    _uiState.value.cajones + listOf(result.data!!)
                                _uiState.update {
                                    it.copy(
                                        cajones = cajonesActualizados,
                                        isLoadingCantidad = false
                                    )
                                }
                                cambiarCajon(result.data!!.id)
                            }

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
    }


    private fun agregarProducto(nombre: String, cantidad: String, imagen: ByteArray) {
        if (nombre.isBlank() || cantidad.isBlank()) {
            _uiState.update { it.copy(error = NOMBRE_CANTIDAD_OBLIGATORIO) }
            return
        } else if (!cantidad.matches(Regex("\\d+"))) {
            _uiState.update { it.copy(error = ERROR_NUMERO_ENTERO) }
            return
        } else if (imagen.isEmpty()) {
            _uiState.update { it.copy(error = IMAGEN_OBLIGATORIA) }
            return
        } else {
            val nuevaCantidad = cantidad.toInt()
            val productoExistente = _uiState.value.productos.find { it.nombre == nombre }
            val cajon = _uiState.value.cajones.find { it.nombre == _uiState.value.cajonActual }
            if (productoExistente != null) {
                _uiState.update { it.copy(error = PRODUCTO_EXISTENTE) }
                return
            }
            viewModelScope.launch {
                agregarProductoUseCase.invoke(nombre, nuevaCantidad, imagen, cajon!!.id)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Success -> {
                                val productosActualizados =
                                    _uiState.value.productos + listOf(result.data!!)
                                _uiState.update {
                                    it.copy(
                                        productos = productosActualizados,
                                        isLoadingCantidad = false
                                    )
                                }
                            }

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
    }

    private fun cambiarCajon(cajonId: String) {
        _uiState.update { it.copy(cajonActual = cajonId) }
        _uiState.value.muebleActual.let { cargarProductos(idCajon = cajonId) }
    }

    private fun cambiarMueble(muebleId: String) {
        _uiState.update { it.copy(muebleActual = muebleId) }
        _uiState.value.cajonActual.let { cargarProductos(idMueble = muebleId) }
    }

    private fun cargarProductos(
        idCajon: String? = null,
        idMueble: String? = null,
        primeraCarga: Boolean = false
    ) {
        viewModelScope.launch {
            cargarProductosUseCase.invoke(idCajon, idMueble).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val muebles = result.data?.muebles ?: emptyList()
                        val cajones = result.data?.cajones ?: emptyList()
                        val productos = result.data?.productos ?: emptyList()
                        val muebleActual = if (primeraCarga) muebles.getOrNull(0)?.nombre
                            ?: "" else muebles.find { it.id == idMueble }?.nombre
                            ?: _uiState.value.muebleActual
                        val idPropietario = result.data?.idPropietario ?: ""
                        val cajonActual =
                            cajones.find { it.id == idCajon }?.nombre ?: _uiState.value.cajonActual
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

    private fun cambiarCantidad(idProducto: Int, aumentar: Boolean) {
        val producto = _uiState.value.productos.find { it.id == idProducto } ?: return
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

    private fun actualizarLista(idProducto: Int, nuevaCantidad: Int) {
        val productosActualizados = if (nuevaCantidad == 0) {
            _uiState.value.productos.filter { it.id != idProducto }
        } else {
            _uiState.value.productos.map { producto ->
                if (producto.id == idProducto) producto.copy(unidades = nuevaCantidad) else producto
            }
        }
        _uiState.update { it.copy(productos = productosActualizados, isLoadingCantidad = false) }
    }
}