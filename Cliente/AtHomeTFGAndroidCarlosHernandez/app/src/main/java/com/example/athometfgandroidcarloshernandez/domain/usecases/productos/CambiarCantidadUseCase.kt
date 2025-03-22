package com.example.athometfgandroidcarloshernandez.domain.usecases.productos

import com.example.athometfgandroidcarloshernandez.data.repositories.ProductosRepository
import javax.inject.Inject


class CambiarCantidadUseCase @Inject constructor(
    private val productosRepository: ProductosRepository
){
    operator fun invoke(idProducto:String, cantidad:Int) = productosRepository.cambiarCantidad(idProducto,cantidad)
}