package com.example.athometfgandroidcarloshernandez.data.model.response

import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.model.MuebleDTO
import com.example.athometfgandroidcarloshernandez.data.model.ProductoDTO

data class CargarProductosResponseDTO (
    val productos: List<ProductoDTO> = emptyList(),
    val cajones: List<CajonDTO> = emptyList(),
    val muebles: List<MuebleDTO> = emptyList(),
    val idPropietario: String = ""
)