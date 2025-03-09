package com.example.athometfgandroidcarloshernandez.domain.usecases.seleccionarcasa

import com.example.athometfgandroidcarloshernandez.data.repositories.CasaRepository
import javax.inject.Inject

class SalirCasaUseCase @Inject constructor(
    private val casaRepository: CasaRepository
){
    operator fun invoke(idUsuario:String,idCasa:String) = casaRepository.salirCasa(idUsuario,idCasa)
}