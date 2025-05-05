package com.example.athometfgandroidcarloshernandez.domain.usecases.productos

import com.example.athometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class AgregarCajonUseCase @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
) {
    operator fun invoke(idMueble:String,nombre:String,idPropietario:String) = inmueblesRepository.agregarCajonConMueble(idMueble, nombre, idPropietario)
}