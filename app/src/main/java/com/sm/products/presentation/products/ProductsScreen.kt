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
import com.sm.products.core.utils.UiState
import com.sm.products.domain.model.Product
import com.sm.products.presentation.products.components.CategoryText
import com.sm.products.presentation.products.components.ProductCard

@Destination<RootGraph>(start = true)
@Composable
fun ProductsScreenRoot(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    //for testability
    ProductsScreen(state)
}

@Composable
fun ProductsScreen(
    state: UiState<Map<String, List<Product>>>
) {

    Scaffold { innerPadding->
        when (state) {
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(state.message.asString(), color = MaterialTheme.colorScheme.error)
                }
            }

            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
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

