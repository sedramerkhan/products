package com.sm.products.presentation.product

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.destinations.ProductDetailsScreenRootDestination
import com.sm.products.core.BaseApplication
import com.sm.products.core.domain.onError
import com.sm.products.core.domain.onSuccess
import com.sm.products.core.presentation.utils.toUiText
import com.sm.products.domain.repository.IProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: IProductRepository,
    application: BaseApplication,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    private val productId  = ProductDetailsScreenRootDestination.argsFrom(savedStateHandle).productId

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.onStart {
        if(_uiState.value.data == null){
            getProduct()
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    private suspend fun fetchProduct(productId: Int) {
        repository.getProduct(productId).onSuccess { product ->
            _uiState.update { it.onSuccess(product) }
        }.onError { error ->
            _uiState.update { it.onError(error.toUiText()) }
        }
    }

    fun getProduct() = viewModelScope.launch {
        _uiState.update { it.onLoading() }
        fetchProduct(productId)
    }


}