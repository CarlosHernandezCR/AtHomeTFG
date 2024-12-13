package com.example.athometfgandroidcarloshernandez.data.model.response

import com.example.athometfgandroidcarloshernandez.data.model.CasaDetallesDTO

data class GetCasasResponseDTO (
    val casas: List<CasaDetallesDTO> = emptyList()
)