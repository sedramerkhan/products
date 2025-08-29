package com.sm.products.data.repository

import com.sm.products.core.data.safeCall
import com.sm.products.core.domain.DataError
import com.sm.products.core.domain.Result
import com.sm.products.core.domain.map
import com.sm.products.core.utils.networkMonitor.NetworkMonitor
import com.sm.products.data.model.ProductResponse
import com.sm.products.data.remote.ProductApi
import com.sm.products.domain.model.Product
import com.sm.products.domain.model.toDomain
import com.sm.products.domain.repository.IProductRepository

class ProductRepository(
    private val api: ProductApi,
    private val networkChecker: NetworkMonitor
) : IProductRepository {
    override suspend fun getProducts(): Result<List<Product>, DataError.Remote> =
        safeCall<List<ProductResponse>>(networkChecker) {
            api.getProducts()
        }.map {
            it.map { it.toDomain() }
        }

    override suspend fun getProduct(id: Int): Result<Product, DataError.Remote> =
        safeCall<ProductResponse>(networkChecker) {
            api.getProduct(id)
        }.map { productResponse: ProductResponse ->
            productResponse.toDomain()
        }
}