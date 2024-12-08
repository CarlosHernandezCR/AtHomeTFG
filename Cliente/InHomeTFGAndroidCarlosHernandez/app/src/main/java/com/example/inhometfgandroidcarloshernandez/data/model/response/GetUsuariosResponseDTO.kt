package com.example.inhometfgandroidcarloshernandez.data.model.response

import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.inmuebles.InmueblesContract

data class GetUsuariosResponseDTO (
    val usuarios: List<InmueblesContract.Usuario>
)