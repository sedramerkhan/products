package com.sm.products.presentation.products.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sm.products.core.presentation.components.image.CoilImage
import com.sm.products.core.presentation.utils.extensions.shimmer
import com.sm.products.domain.model.Product

@Composable
fun ProductCard(product: Product,
                onClick: () -> Unit = {}){
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier.padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ///Image
            CoilImage(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                path = product.imageUrl
            )
            Spacer(Modifier.height(8.dp))

            ///Title
            Text(
                product.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))

            ///Price
            Text(
                "$${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}


@Composable
fun ProductCardShimmer() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier.padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ///Image
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .shimmer(),
            )
            Spacer(Modifier.height(8.dp))

            ///Title
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(150.dp)
                    .shimmer()
            )
            Spacer(Modifier.height(4.dp))

            ///Price
            Box(
                modifier = Modifier
                    .height(15.dp)
                    .width(100.dp)
                    .shimmer()
            )
        }
    }
}