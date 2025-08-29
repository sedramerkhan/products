package com.sm.products.presentation.products.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun CategoryText(
    category: String
){
    Text(
        text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(top =16.dp, bottom = 8.dp),
        color = MaterialTheme.colorScheme.secondary
    )
}