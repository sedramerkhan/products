package com.sm.products.presentation.products.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sm.products.core.presentation.utils.extensions.shimmer
import com.sm.products.domain.model.Product

@Composable
fun ProductsList(
    modifier: Modifier,
    data: Map<String, List<Product>>,
    onClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.forEach { category, products ->

            // Category should span across 2 columns
            item(span = { GridItemSpan(maxLineSpan) }) {
                CategoryText(category)
            }

            items(products) { product ->
                ProductCard(product, onClick = { onClick(product.id) })
            }
        }
    }
}

@Composable
fun ProductsListShimmer(
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .height(30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight()

                        .shimmer(cornerRadius = 8.dp)
                )
            }
        }

        items(6) {
            ProductCardShimmer()
        }

    }
}