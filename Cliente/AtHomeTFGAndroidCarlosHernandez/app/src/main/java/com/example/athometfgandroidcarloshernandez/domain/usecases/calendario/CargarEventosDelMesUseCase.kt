package com.example.athometfgandroidcarloshernandez.domain.usecases.calendario

import com.example.athometfgandroidcarloshernandez.data.repositories.EventosRepository
import javax.inject.Inject

class CargarEventosDelMesUseCase @Inject constructor(
    private val eventosRepository: EventosRepository
){
    operator fun invoke(idCasa:String,mes:Int,anio:Int) = eventosRepository.cargarEventosDelMes(idCasa,mes,anio)
}