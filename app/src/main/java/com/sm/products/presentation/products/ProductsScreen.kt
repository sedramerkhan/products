package com.sm.products.presentation.products

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.sm.products.core.presentation.components.ErrorView
import com.sm.products.core.presentation.components.PullToRefreshBox
import com.sm.products.core.presentation.components.CustomToastHost
import com.sm.products.presentation.products.components.ProductsList
import com.sm.products.presentation.products.components.ProductsListShimmer

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
    Log.d("custum", "ProductsScreen ${state}")

    Scaffold { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        if (state.data.isNotEmpty()) {
            PullToRefreshBox(isRefreshing = state.isRefreshing, onRefresh = pullToRefresh) {
                ProductsList(modifier, state.data)
            }

            if (state.error != null) {
                CustomToastHost(state.error.asString())
            }
        } else if (state.isLoading) {
            ProductsListShimmer(modifier)
        } else if (state.error != null)
            ErrorView(modifier, state.error.asString(), onRetry = getProduct)
    }

}

