package com.example.athometfgandroidcarloshernandez.domain.usecases.productos

import com.example.athometfgandroidcarloshernandez.data.repositories.ProductosRepository
import javax.inject.Inject

class PedirPrestadoUseCase @Inject constructor(
    private val productosRepository: ProductosRepository
){
    operator fun invoke(productoId:String,idUsuario:String) = productosRepository.pedirPrestado(productoId,idUsuario)
}