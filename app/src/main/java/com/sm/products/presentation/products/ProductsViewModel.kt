package com.sm.products.presentation.products

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.products.core.domain.onError
import com.sm.products.core.domain.onSuccess
import com.sm.products.core.utils.toUiText
import com.sm.products.domain.model.Product
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
class ProductsViewModel @Inject constructor(
    private val repository: IProductRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState
        .onStart {
            if (_uiState.value.data.isNotEmpty()) {
                getProducts()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    var products: Map<String, List<Product>> = mutableStateMapOf()


    private suspend fun fetchProducts() {
        repository.getProducts().onSuccess { data->
            _uiState.update { it.onSuccess(data.groupBy { it.category }.toMutableMap())}
        }.onError { error ->
            _uiState.update { it.onError(error.toUiText())}
        }
    }

    fun getProducts() = viewModelScope.launch {
        _uiState.update { it.onLoading() }
        fetchProducts()
    }

    fun onPullToRefreshTrigger() = viewModelScope.launch {
        _uiState.update { it.onRefreshing() }
        fetchProducts()
    }

}