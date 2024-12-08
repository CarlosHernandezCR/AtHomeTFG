package com.example.inhometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.inhometfgandroidcarloshernandez.data.repositories.UsuarioRepository
import javax.inject.Inject

class GetUsuariosUseCase @Inject constructor(
    private val usuariosRepository: UsuarioRepository
) {
    operator fun invoke(idCasa:Int) = usuariosRepository.getUsuarios(idCasa)
}