package com.example.athometfgandroidcarloshernandez.domain.usecases.calendario

import com.example.athometfgandroidcarloshernandez.data.repositories.EventosRepository
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioContract
import javax.inject.Inject

class CrearEventoUseCase @Inject constructor(
    private val eventosRepository: EventosRepository
){
    operator fun invoke(idCasa: String, eventoCasa: CalendarioContract.EventoCasa, fecha:String) = eventosRepository.crearEvento(idCasa, eventoCasa, fecha)
}