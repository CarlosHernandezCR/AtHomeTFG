package com.example.inhometfgandroidcarloshernandez.data.model.request

import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioContract

data class CrearEventoRequestDTO (
    var idCasa: Int,
    var eventoCasa: CalendarioContract.EventoCasa,
    var fecha:String
)