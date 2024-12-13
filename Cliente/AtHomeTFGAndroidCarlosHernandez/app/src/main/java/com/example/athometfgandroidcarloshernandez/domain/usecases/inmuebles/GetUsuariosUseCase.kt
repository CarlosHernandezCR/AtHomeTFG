package com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.athometfgandroidcarloshernandez.data.repositories.UsuarioRepository
import javax.inject.Inject

class GetUsuariosUseCase @Inject constructor(
    private val usuariosRepository: UsuarioRepository
) {
    operator fun invoke(idCasa:String) = usuariosRepository.getUsuarios(idCasa)
}