package com.example.athometfgandroidcarloshernandez.data.model.request

import com.example.athometfgandroidcarloshernandez.ui.framework.screens.calendario.CalendarioContract

data class CrearEventoRequestDTO (
    var idCasa: String,
    var eventoCasa: CalendarioContract.EventoCasa,
    var fecha:String
)