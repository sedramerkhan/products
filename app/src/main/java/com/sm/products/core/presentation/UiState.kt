package com.sm.products.core.presentation


sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: UiText) : UiState<Nothing>()
}