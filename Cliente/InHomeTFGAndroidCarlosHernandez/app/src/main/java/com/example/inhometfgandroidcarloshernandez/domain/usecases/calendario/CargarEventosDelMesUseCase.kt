package com.example.inhometfgandroidcarloshernandez.domain.usecases.calendario

import com.example.inhometfgandroidcarloshernandez.data.repositories.EventosRepository
import javax.inject.Inject

class CargarEventosDelMesUseCase @Inject constructor(
    private val eventosRepository: EventosRepository
){
    operator fun invoke(idCasa:String,mes:Int,anio:Int) = eventosRepository.cargarEventosDelMes(idCasa,mes,anio)
}