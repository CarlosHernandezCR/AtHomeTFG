package com.example.inhometfgandroidcarloshernandez.domain.usecases.seleccionarcasa

import com.example.inhometfgandroidcarloshernandez.data.repositories.CasaRepository
import javax.inject.Inject

class GetCasasUseCase @Inject constructor(
    private val casaRepository: CasaRepository
){
    operator fun invoke(idUsuario:String) = casaRepository.getCasas(idUsuario)
}