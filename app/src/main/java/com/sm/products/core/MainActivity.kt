package com.sm.products.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.sm.products.core.presentation.theme.ProductsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductsTheme {

                    val engine = rememberNavHostEngine()
                    val navController = engine.rememberNavController()
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        engine = engine,
                        navController = navController
                    )
                     //First Destination is ProductsScreenRoot and it navigate to
                // ProductDetailsScreenRoot

            }
        }
    }
}
