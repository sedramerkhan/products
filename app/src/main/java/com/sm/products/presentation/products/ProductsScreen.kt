package com.sm.products.presentation.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.sm.products.core.presentation.components.ErrorView
import com.sm.products.core.utils.UiState
import com.sm.products.domain.model.Product
import com.sm.products.presentation.products.components.CategoryText
import com.sm.products.presentation.products.components.ProductCard
import com.sm.products.presentation.products.components.ProductsLoader

@Destination<RootGraph>(start = true)
@Composable
fun ProductsScreenRoot(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    //for testability
    ProductsScreen(state, viewModel::getProducts)
}

@Composable
fun ProductsScreen(
    state: UiState<Map<String, List<Product>>>,
    getProduct: () -> Unit,
) {


    Scaffold { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        when (state) {
            is UiState.Loading -> {
                ProductsLoader(modifier)
            }

            is UiState.Error -> {
                ErrorView(modifier, state.message.asString(), onRetry = getProduct)
            }

            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier,
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.data.forEach { category, products ->

                        // Category should span across 2 columns
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            CategoryText(category)
                        }

                        items(products) { product ->
                            ProductCard(product)
                        }


                    }
                }
            }
        }

    }
}

