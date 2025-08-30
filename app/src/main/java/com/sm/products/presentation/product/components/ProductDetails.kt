package com.sm.products.presentation.product.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sm.products.core.presentation.components.image.CoilImage
import com.sm.products.core.presentation.utils.extensions.shimmer
import com.sm.products.domain.model.Product


@Composable
fun ProductDetails(
    product: Product,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {


    val itemsModifier = Modifier.padding(horizontal = 16.dp)
    Column(
        modifier = modifier
            .padding(top = 16.dp, bottom = 32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Product Image
        Box {
            CoilImage(
                path = product.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            IconButton(
                onClick = onBackClicked,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(.6f)
                )
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
                    contentDescription = ""
                )
            }
        }

        // Product Title
        Text(
            modifier = itemsModifier,
            text = product.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Product Price
        Text(
            modifier = itemsModifier,
            text = "$${product.price}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )

        // Product Category
        ProductCategory(
            modifier = itemsModifier,
            product.category
        )

        // Product Rating
        ProductRating(
            modifier = itemsModifier,
            product.rating.value, product.rating.reviewsCount
        )


        // Product Description
        ProductDescription(
            modifier = itemsModifier,
            product.description
        )

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
