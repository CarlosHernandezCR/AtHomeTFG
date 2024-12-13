package com.example.athometfgandroidcarloshernandez.domain.usecases.estados

import com.example.athometfgandroidcarloshernandez.data.repositories.EstadosRepository
import javax.inject.Inject

class GetDatosCasaUseCase @Inject constructor(
    private val estadosRepository: EstadosRepository
){
    operator fun invoke(idUsuario:String,idCasa:String) = estadosRepository.getDatosCasa(idUsuario,idCasa)
}