package com.sm.products.domain.model

import com.sm.products.data.model.ProductResponse
import com.sm.products.data.model.RatingResponse

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val imageUrl: String,
    val rating: Rating
)

data class Rating(
    val value: Double,
    val reviewsCount: Int
)

fun ProductResponse.toDomain(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    imageUrl = image,
    rating = rating.toDomain()
)

fun RatingResponse.toDomain(): Rating = Rating(
    value = rate,
    reviewsCount = count
)
