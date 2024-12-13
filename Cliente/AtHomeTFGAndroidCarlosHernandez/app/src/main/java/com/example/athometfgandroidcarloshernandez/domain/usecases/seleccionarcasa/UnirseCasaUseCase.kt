package com.example.athometfgandroidcarloshernandez.domain.usecases.seleccionarcasa

import com.example.athometfgandroidcarloshernandez.data.repositories.CasaRepository
import javax.inject.Inject

class UnirseCasaUseCase @Inject constructor(
    private val casaRepository: CasaRepository
){
    operator fun invoke(idUsuario: String, codigoInvitacion: String) = casaRepository.unirseCasa(idUsuario, codigoInvitacion)
}