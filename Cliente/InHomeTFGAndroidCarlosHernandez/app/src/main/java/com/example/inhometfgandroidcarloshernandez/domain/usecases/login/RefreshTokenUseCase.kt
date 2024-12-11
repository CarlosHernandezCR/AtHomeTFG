package com.example.inhometfgandroidcarloshernandez.domain.usecases.login

import com.example.inhometfgandroidcarloshernandez.data.repositories.UsuarioRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    operator fun invoke(token: String) = usuarioRepository.refreshToken(token)
}