package com.example.athometfgandroidcarloshernandez.domain.usecases.estados

import com.example.athometfgandroidcarloshernandez.data.repositories.EstadosRepository
import javax.inject.Inject

class NuevoEstadoUseCase @Inject constructor(
    private val estadosRepository: EstadosRepository
){
    operator fun invoke(estado:String,color:String,idUsuario:String) = estadosRepository.crearEstado(estado,color,idUsuario)
}