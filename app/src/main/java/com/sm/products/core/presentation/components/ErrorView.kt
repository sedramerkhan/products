package com.sm.products.core.presentation.components

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRetry: (() -> Unit)? = null,
    retryIcon: ImageVector = Icons.Default.Refresh,
    retryText: String = "Retry"
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = rememberVectorPainter(Icons.Default.Warning),
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(100.dp),
                contentDescription = ""
            )


            Text(
                text = errorMessage,
                style =  MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )

            if (onRetry != null) {
                Button(
                    modifier = Modifier.padding(top = 32.dp),
                    onClick = onRetry
                ) {
                    Icon(
                        imageVector = retryIcon,
                        contentDescription = "Retry",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(retryText)
                }
            }
        }
    }
}
