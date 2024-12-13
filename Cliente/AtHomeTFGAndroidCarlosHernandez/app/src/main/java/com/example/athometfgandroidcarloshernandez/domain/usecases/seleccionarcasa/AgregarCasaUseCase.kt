package com.example.athometfgandroidcarloshernandez.domain.usecases.seleccionarcasa

import com.example.athometfgandroidcarloshernandez.data.repositories.CasaRepository
import javax.inject.Inject

class AgregarCasaUseCase @Inject constructor(
    private val casaRepository: CasaRepository
){
    operator fun invoke( idUsuario: String,nombre: String, direccion: String, codigoPostal: String) = casaRepository.agregarCasa(idUsuario, nombre, direccion, codigoPostal)
}