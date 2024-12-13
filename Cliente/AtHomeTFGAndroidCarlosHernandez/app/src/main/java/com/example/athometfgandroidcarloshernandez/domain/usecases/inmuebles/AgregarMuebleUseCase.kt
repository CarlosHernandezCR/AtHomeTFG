package com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.athometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class AgregarMuebleUseCase @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
) {
    operator fun invoke(idCasa: String, idHabitacion:String, nombre:String) = inmueblesRepository.agregarMueble(idCasa, idHabitacion, nombre)
}