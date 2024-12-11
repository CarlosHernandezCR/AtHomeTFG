package com.example.inhometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.inhometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class AgregarMuebleUseCase @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
) {
    operator fun invoke(idCasa: String, nombreHabitacion:String, nombre:String) = inmueblesRepository.agregarMueble(idCasa, nombreHabitacion, nombre)
}