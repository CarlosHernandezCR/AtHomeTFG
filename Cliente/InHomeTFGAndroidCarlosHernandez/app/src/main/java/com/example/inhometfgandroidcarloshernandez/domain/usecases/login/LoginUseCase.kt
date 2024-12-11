package com.example.inhometfgandroidcarloshernandez.domain.usecases.login

import com.example.inhometfgandroidcarloshernandez.data.repositories.UsuarioRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
){
    operator fun invoke(identificador: String, password: String) = usuarioRepository.login(identificador,password)
}