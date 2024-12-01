package com.example.inhometfgandroidcarloshernandez.domain.usecases.calendario

import com.example.inhometfgandroidcarloshernandez.data.repositories.EventosRepository
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioContract
import javax.inject.Inject

class CrearEventoUseCase @Inject constructor(
    private val eventosRepository: EventosRepository
){
    operator fun invoke(idCasa: Int, eventoCasa: CalendarioContract.EventoCasa, fecha:String) = eventosRepository.crearEvento(idCasa, eventoCasa, fecha)
}