package com.sm.products.presentation.products.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sm.products.core.utils.extensions.shimmer

@Composable
fun ProductsLoader(
  modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //todo: check this
        item {
            Box(
                Modifier .padding(top = 16.dp, bottom = 8.dp)
                    .height(30.dp)
                    .shimmer(cornerRadius = 8.dp).width(100.dp)
            )
        }
        item{}

        items(6) {
            Box(
                Modifier
                    .height(250.dp)
                    .shimmer(cornerRadius = 8.dp)
            )
        }

    }
}