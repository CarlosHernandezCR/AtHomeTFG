package com.example.inhometfgandroidcarloshernandez.domain.usecases.estados

import com.example.inhometfgandroidcarloshernandez.data.repositories.EstadosRepository
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val estadosRepository: EstadosRepository
){
    operator fun invoke(id:Int) = estadosRepository.getHomeData(id)
}