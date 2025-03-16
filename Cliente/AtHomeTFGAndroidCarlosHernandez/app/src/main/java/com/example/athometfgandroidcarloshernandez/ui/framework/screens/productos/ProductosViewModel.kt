package com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos

import androidx.lifecycle.ViewModel
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.productos.ProductosContract.ProductosEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ProductosViewModel @Inject constructor(
    
    ): ViewModel(){
    private val _uiState = MutableStateFlow(ProductosContract.ProductosState())
    val uiState: StateFlow<ProductosContract.ProductosState> = _uiState.asStateFlow()

    fun handleEvent(event: ProductosEvent) {
        when (event) {
            is ProductosEvent.ErrorMostrado -> _uiState.update { it.copy(error = null)}

        }
    }


}

