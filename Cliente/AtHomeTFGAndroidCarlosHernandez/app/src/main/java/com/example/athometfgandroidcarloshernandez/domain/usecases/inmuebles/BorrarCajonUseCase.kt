package com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.athometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class BorrarCajonUseCase @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
) {
    operator fun invoke(idCajon:String) = inmueblesRepository.borrarCajon(idCajon)
}