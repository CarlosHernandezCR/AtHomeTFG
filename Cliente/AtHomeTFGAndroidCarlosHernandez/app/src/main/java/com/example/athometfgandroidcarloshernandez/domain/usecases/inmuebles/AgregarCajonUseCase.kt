package com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.athometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class AgregarCajonUseCase @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
) {
    operator fun invoke(idCasa:String,nombreHabitacion:String,nombreMueble:String,nombre:String,idPropietario:String) = inmueblesRepository.agregarCajon(idCasa, nombreHabitacion, nombreMueble, nombre, idPropietario)
}