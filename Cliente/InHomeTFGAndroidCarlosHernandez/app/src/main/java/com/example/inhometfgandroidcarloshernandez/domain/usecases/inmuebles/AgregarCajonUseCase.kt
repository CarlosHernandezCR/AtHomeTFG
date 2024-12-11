package com.example.inhometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.inhometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.inhometfgandroidcarloshernandez.data.model.MuebleDTO
import com.example.inhometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class AgregarCajonUseCase @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
) {
    operator fun invoke(idCasa:String,nombreHabitacion:String,nombreMueble:String,nombre:String,idPropietario:String) = inmueblesRepository.agregarCajon(idCasa, nombreHabitacion, nombreMueble, nombre, idPropietario)
}