package com.example.athometfgandroidcarloshernandez.domain.usecases.calendario

import com.example.athometfgandroidcarloshernandez.data.repositories.EventosRepository
import javax.inject.Inject

class VotarUseCase @Inject constructor(
    private val repository: EventosRepository
) {
    fun invoke(idEvento: String,idUsuario:String) = repository.votar(idEvento,idUsuario)
}