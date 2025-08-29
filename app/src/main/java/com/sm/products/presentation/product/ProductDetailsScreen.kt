package com.sm.products.presentation.product

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.sm.products.core.presentation.components.ErrorView
import com.sm.products.presentation.product.components.ProductDetails
import com.sm.products.presentation.product.components.ProductDetailsShimmer

@Destination<RootGraph>(style = ProductsDetailsTransitions::class)
@Composable
fun ProductDetailsScreenRoot(
    productId: Int,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ProductDetailsScreen(
        state = state,
        onRetry = viewModel::getProduct,
    )
}


@Composable
fun ProductDetailsScreen(
    state: ProductDetailsUiState,
    onRetry: () -> Unit
) {

    Scaffold { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        if (state.data != null) {
            ProductDetails(
                product = state.data,
                modifier = Modifier.fillMaxSize()
            )
        } else if (state.isLoading) {
            ProductDetailsShimmer(modifier)

        } else if (state.error != null) {
            ErrorView(
                modifier = Modifier,
                errorMessage = state.error.asString(),
                onRetry = onRetry
            )
        }

    }
}

