package com.example.inhometfgandroidcarloshernandez.data.model.response

import com.example.inhometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.inhometfgandroidcarloshernandez.data.model.HabitacionDTO
import com.example.inhometfgandroidcarloshernandez.data.model.MuebleDTO

data class PantallaInmueblesResponseDTO (
    val habitaciones: List<HabitacionDTO> = emptyList(),
    val muebles: List<MuebleDTO> = emptyList(),
    val cajones: List<CajonDTO> = emptyList()
)