package com.example.athometfgandroidcarloshernandez.domain.usecases.seleccionarcasa

import com.example.athometfgandroidcarloshernandez.data.repositories.CasaRepository
import javax.inject.Inject

class GetCasasUseCase @Inject constructor(
    private val casaRepository: CasaRepository
){
    operator fun invoke(idUsuario:String) = casaRepository.getCasas(idUsuario)
}