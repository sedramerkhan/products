package com.sm.products.core.presentation.components.image

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sm.products.R

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    path: String,
    placeholder: Int =R.drawable.ic_product_placeholder,
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = path,
        onSuccess = {
            imageLoadResult =
                it.painter.intrinsicSize.let { size ->
                    if (size.width > 1 && size.height > 1) {
                        Result.success(it.painter)
                    } else {
                        Result.failure(Exception("Invalid image size"))
                    }
                }

        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    val painterState by painter.state.collectAsStateWithLifecycle()
    val transition by animateFloatAsState(
        targetValue = if (painterState is AsyncImagePainter.State.Success) {
            1f
        } else {
            0f
        },
        animationSpec = tween(durationMillis = 800)
    )

    when (val result = imageLoadResult) {
        null -> Box(modifier, contentAlignment = Alignment.Center) {
            PulseAnimation(
                modifier = Modifier.size(60.dp)
            )
        }

        else -> {
            Image(
                painter = if (result.isSuccess) painter else {
                    painterResource(placeholder)
                },
                colorFilter = if (result.isFailure) ColorFilter.tint(MaterialTheme.colorScheme.secondary) else null, // Set your color here

                contentDescription = "",
                contentScale =
                    ContentScale.Fit,
                modifier = modifier
                    .graphicsLayer {
                        rotationX = (1f - transition) * 30f
                        val scale = 0.8f + (0.2f * transition)
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
    }
}