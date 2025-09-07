package com.sm.products.core.networkMonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /** For use in repositories / viewModels */
    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /** For UI (Compose) – observe as Flow */
    fun observeConnectivityAsFlow() = callbackFlow {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // thread-safe set of active networks
        val activeNetworks = ConcurrentHashMap.newKeySet<Network>()

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                activeNetworks.add(network)
                trySend(getCurrentState(activeNetworks))
            }

            override fun onLost(network: Network) {
                activeNetworks.remove(network)
                trySend(getCurrentState(activeNetworks))
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        cm.registerNetworkCallback(request, callback)

        // send current state immediately
        val initialState = if (isConnected()) ConnectionState.Available else ConnectionState.Unavailable
        trySend(initialState)

        awaitClose { cm.unregisterNetworkCallback(callback) }
    }

    private fun getCurrentState(activeNetworks: Set<Network>): ConnectionState {
        return if (activeNetworks.isNotEmpty()) ConnectionState.Available else ConnectionState.Unavailable
    }
}

/** Composable helper (no need to inject anything in view) */
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    val monitor = rememberNetworkMonitor(context)
    return produceState(initialValue = monitor.isConnectedState()) {
        monitor.observeConnectivityAsFlow().collect { value = it }
    }
}

/** Small helpers for Compose-only usage */
private fun NetworkMonitor.isConnectedState(): ConnectionState =
    if (isConnected()) ConnectionState.Available else ConnectionState.Unavailable

@Composable
private fun rememberNetworkMonitor(context: Context): NetworkMonitor {
    // no injection needed in UI
    return NetworkMonitor(context.applicationContext)
}
