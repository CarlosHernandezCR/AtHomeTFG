package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inhometfgandroidcarloshernandez.domain.usecases.estados.GetHomeDataUseCase
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstadosViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstadosContract.EstadosState())
    val uiState: StateFlow<EstadosContract.EstadosState> = _uiState.asStateFlow()

    init {
        getHomeData()
    }
    //todo Pasar el id del usuario desde la pantalla de login y hacer la llamada
    private fun getHomeData() {
//        viewModelScope.launch {
//            _uiState.value = _uiState.value.copy(loading = true)
//            when (val result = getHomeDataUseCase()) {
//                is NetworkResult.Success -> {
//                    val data = result.data
//                    _uiState.value = _uiState.value.copy(
//                        loading = false,
//                        nombreCasa = data?.nombreCasa,
//                        direccion = data?.direccion,
//                        usuariosCasa = data?.usuariosCasa ?: emptyList(),
//                        estadoActual = data?.estadoActual
//                    )
//                }
//                is NetworkResult.Error -> {
//                    _uiState.value = _uiState.value.copy(
//                        loading = false,
//                        error = result.message
//                    )
//                }
//                is NetworkResult.Loading -> {
//                    _uiState.value = _uiState.value.copy(loading = true)
//                }
//            }
//        }
    }
}
