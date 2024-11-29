package com.example.inhometfgandroidcarloshernandez.domain.usecases.calendario

import com.example.inhometfgandroidcarloshernandez.data.repositories.EventosRepository
import javax.inject.Inject

class CargarEventosDelDiaUseCase @Inject constructor(
    private val eventosRepository: EventosRepository
){
    operator fun invoke(idCasa:Int,dia:Int,mes:Int,anio:Int) = eventosRepository.cargarEventosDelDia(idCasa,dia,mes,anio)
}