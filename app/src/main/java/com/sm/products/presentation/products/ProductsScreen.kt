package com.sm.products.presentation.products

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.sm.products.core.presentation.components.ErrorView
import com.sm.products.core.presentation.components.PullToRefreshBox
import com.sm.products.core.utils.CustomToastHost
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
    ProductsScreen(state, viewModel::onPullToRefreshTrigger, viewModel::getProducts)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    state: ProductsUiState,
    pullToRefresh: () -> Unit,
    getProduct: () -> Unit,
) {
    Log.d("cust", "dff ${state}")




    Scaffold { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        if (state.data.isNotEmpty()) {
            PullToRefreshBox(isRefreshing = state.isRefreshing, onRefresh = pullToRefresh) {
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

            if (state.error != null) {
                CustomToastHost(state.error.asString())
            }
        } else if (state.isLoading) {
            ProductsLoader(modifier)
        } else if (state.error != null)
            ErrorView(modifier, state.error.asString(), onRetry = getProduct)
    }

}

