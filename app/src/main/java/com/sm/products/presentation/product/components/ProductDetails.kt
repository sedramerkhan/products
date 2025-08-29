package com.sm.products.presentation.product.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sm.products.core.presentation.components.image.CoilImage
import com.sm.products.core.utils.extensions.shimmer
import com.sm.products.domain.model.Product


@Composable
fun ProductDetails(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Product Image
        CoilImage(
            path = product.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        // Product Title
        Text(
            text = product.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Product Price
        Text(
            text = "$${product.price}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )

        // Product Category
        ProductCategory(product.category)

        // Product Rating
        ProductRating(product.rating.value, product.rating.reviewsCount)


        // Product Description
        ProductDescription(product.description)

    }
}



@Composable
fun ProductDetailsShimmer(modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shimmer()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(24.dp)
                .shimmer()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(20.dp)
                .shimmer()
        )

        repeat(4) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .shimmer()
            )
        }
    }
}
