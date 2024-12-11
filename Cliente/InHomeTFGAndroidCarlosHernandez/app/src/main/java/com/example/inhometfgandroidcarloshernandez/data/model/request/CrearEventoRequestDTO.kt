package com.example.inhometfgandroidcarloshernandez.data.model.request

import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioContract

data class CrearEventoRequestDTO (
    var idCasa: String,
    var eventoCasa: CalendarioContract.EventoCasa,
    var fecha:String
)