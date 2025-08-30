package com.sm.products.presentation.products.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sm.products.core.presentation.utils.extensions.shimmer
import java.util.Locale

@Composable
fun CategoryText(
    category: String
) {
    Text(
        text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun CategoryTextShimmer(

) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 8.dp)
            .height(25.dp)
    ) {

        Box(
            modifier = Modifier
                .width(200.dp)
                .fillMaxHeight()

                .shimmer(cornerRadius = 8.dp)
        )
    }
}