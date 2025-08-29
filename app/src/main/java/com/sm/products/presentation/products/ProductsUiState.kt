package com.sm.products.presentation.products

import com.sm.products.core.utils.UiText
import com.sm.products.domain.model.Product

data class ProductsUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val data: Map<String, List<Product>> = mapOf(),
    val error: UiText? = null
) {
    fun onLoading() =
        copy(isLoading = true, isRefreshing = false, error = null)


    fun onSuccess(data: Map<String, List<Product>>) =
        copy(isLoading = false, isRefreshing = false, error = null, data = data)


    fun onError(error: UiText) =
        copy(isLoading = false, isRefreshing = false, error = error)


    fun onRefreshing() = copy(
        isLoading = false,
        isRefreshing = true,
        error = null
    )

}
