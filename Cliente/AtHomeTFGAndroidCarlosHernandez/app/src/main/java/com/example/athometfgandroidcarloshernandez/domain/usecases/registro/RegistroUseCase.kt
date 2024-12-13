package com.example.athometfgandroidcarloshernandez.domain.usecases.registro

import com.example.athometfgandroidcarloshernandez.data.repositories.UsuarioRepository
import javax.inject.Inject

class RegistroUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
){
    operator fun invoke(nombre:String,password:String,correo:String,telefono:String,color:String) = usuarioRepository.registro(nombre,password,correo,telefono,color)
}