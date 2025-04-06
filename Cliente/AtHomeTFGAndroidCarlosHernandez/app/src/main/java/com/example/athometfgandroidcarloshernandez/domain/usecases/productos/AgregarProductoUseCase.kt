package com.example.athometfgandroidcarloshernandez.domain.usecases.productos

import com.example.athometfgandroidcarloshernandez.data.repositories.ProductosRepository
import javax.inject.Inject


class AgregarProductoUseCase @Inject constructor(
    private val productosRepository: ProductosRepository
){
    operator fun invoke(nombre:String, cantidad:Int,imagen:String, idcajon:String) = productosRepository.agregarProducto(nombre, cantidad,imagen, idcajon)
}