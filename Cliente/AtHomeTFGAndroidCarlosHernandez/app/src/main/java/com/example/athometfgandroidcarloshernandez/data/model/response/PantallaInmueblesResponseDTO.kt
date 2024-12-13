package com.example.athometfgandroidcarloshernandez.data.model.response

import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.HabitacionDTO
import com.example.athometfgandroidcarloshernandez.data.model.MuebleDTO

data class PantallaInmueblesResponseDTO (
    val habitaciones: List<HabitacionDTO> = emptyList(),
    val muebles: List<MuebleDTO> = emptyList(),
    val cajones: List<CajonDTO> = emptyList()
)