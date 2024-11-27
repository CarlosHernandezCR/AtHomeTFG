package com.example.inhometfgandroidcarloshernandez.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(): ViewModel() {
    var idUsuario: Int by mutableIntStateOf(0)
        private set

    var idCasa: Int by mutableIntStateOf(0)
        private set

    fun updateIdUsuario(newId: Int) {
        idUsuario = newId
    }

    fun updateIdCasa(newId: Int) {
        idCasa = newId
    }
}