package com.sm.products.domain.repository

import com.sm.products.core.domain.DataError
import com.sm.products.core.domain.Result
import com.sm.products.domain.model.Product

interface IProductRepository {
    suspend fun getProducts(): Result<List<Product>, DataError.Remote>
    suspend fun getProduct(id: Int): Result<Product, DataError.Remote>
}
