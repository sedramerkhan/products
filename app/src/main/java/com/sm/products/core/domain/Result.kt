package com.sm.products.core.domain


sealed interface Result<out D, out E: AppError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E: AppError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E: AppError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <T, E: AppError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Success -> {
            action(data)
            this
        }
        else -> this
    }
}

inline fun <T, E: AppError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        else -> this
    }
}

typealias EmptyResult<E> = Result<Unit, E>
