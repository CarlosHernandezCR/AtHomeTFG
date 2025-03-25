package com.example.athometfgandroidcarloshernandez.domain.usecases.productos

import com.example.athometfgandroidcarloshernandez.data.repositories.ProductosRepository
import javax.inject.Inject

class CargarProductosUseCase @Inject constructor(
    private val productosRepository: ProductosRepository
){
    operator fun invoke(idCajon:String) = productosRepository.cargarProductos(idCajon)
}