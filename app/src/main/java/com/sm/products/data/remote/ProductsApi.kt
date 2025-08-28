package com.sm.products.data.remote

import com.sm.products.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {


    @GET("products")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int,
    ): Response<ProductResponse>
}