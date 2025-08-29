package com.sm.products.presentation.products

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.products.core.domain.onError
import com.sm.products.core.domain.onSuccess
import com.sm.products.core.utils.UiState
import com.sm.products.core.utils.toUiText
import com.sm.products.domain.model.Product
import com.sm.products.domain.repository.IProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: IProductRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState<Map<String, List<Product>>>>(UiState.Loading)
    val uiState: StateFlow<UiState<Map<String, List<Product>>>> = _uiState
        .onStart {
            if (_uiState.value !is UiState.Success) {
                getProducts()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    var products: Map<String, List<Product>> = mutableStateMapOf()


    private suspend fun fetchProducts() {
        repository.getProducts().onSuccess {
            products = it.groupBy { it.category }.toMutableMap()
            _uiState.value = UiState.Success(products)
        }.onError {
            _uiState.value = UiState.Error(it.toUiText())
        }
    }

    fun getProducts() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        fetchProducts()
    }

    fun onPullToRefreshTrigger() = viewModelScope.launch {
        _isRefreshing.value = true
        fetchProducts()
        _isRefreshing.value = false
    }

}