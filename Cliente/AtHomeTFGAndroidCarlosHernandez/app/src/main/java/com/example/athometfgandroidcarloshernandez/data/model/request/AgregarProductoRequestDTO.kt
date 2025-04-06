package com.example.athometfgandroidcarloshernandez.data.model.request

data class AgregarProductoRequestDTO (
    val nombre: String,
    val cantidad: Int,
    val imagen:String,
    val idCajon: String
)