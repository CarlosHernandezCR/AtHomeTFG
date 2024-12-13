package com.example.athometfgandroidcarloshernandez.domain.usecases.login

import com.example.athometfgandroidcarloshernandez.data.repositories.UsuarioRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    operator fun invoke(token: String) = usuarioRepository.refreshToken(token)
}