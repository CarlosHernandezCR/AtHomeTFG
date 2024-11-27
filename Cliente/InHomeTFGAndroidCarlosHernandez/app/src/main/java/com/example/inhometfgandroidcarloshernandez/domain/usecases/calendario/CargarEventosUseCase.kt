package com.example.inhometfgandroidcarloshernandez.domain.usecases.calendario

import com.example.inhometfgandroidcarloshernandez.data.repositories.EventosRepository
import javax.inject.Inject

class CargarEventosUseCase @Inject constructor(
    private val eventosRepository: EventosRepository
){
    operator fun invoke(idCasa:Int,mes:Int,anio:Int) = eventosRepository.cargarEventos(idCasa,mes,anio)
}