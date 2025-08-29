package com.sm.products.presentation.product

import com.sm.products.core.utils.UiText
import com.sm.products.domain.model.Product

data class ProductUiState(
    val isLoading: Boolean = true,
    val data: Product?= null,
    val error: UiText? = null
) {
    fun onLoading() =
        copy(isLoading = true, error = null)


    fun onSuccess(data: Product) =
        copy(isLoading = false,  error = null, data = data)


    fun onError(error: UiText) =
        copy(isLoading = false, error = error)

}
