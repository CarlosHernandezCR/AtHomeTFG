package com.example.athometfgandroidcarloshernandez.data.model.response

import com.example.athometfgandroidcarloshernandez.ui.framework.screens.inmuebles.InmueblesContract

data class GetUsuariosResponseDTO (
    val usuarios: List<InmueblesContract.UsuarioInmuebles>
)