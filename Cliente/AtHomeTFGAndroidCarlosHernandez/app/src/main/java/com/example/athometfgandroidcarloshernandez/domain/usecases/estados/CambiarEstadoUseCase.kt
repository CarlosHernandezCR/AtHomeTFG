package com.example.athometfgandroidcarloshernandez.domain.usecases.estados

import com.example.athometfgandroidcarloshernandez.data.repositories.EstadosRepository
import com.example.athometfgandroidcarloshernandez.data.repositories.UsuarioRepository
import javax.inject.Inject

class CambiarEstadoUseCase @Inject constructor(
    private val usuarioRepository: EstadosRepository
){
    operator fun invoke(estado: String, idCasa:String, idUsuario: String) = usuarioRepository.cambiarEstado(estado,idCasa,idUsuario)
}