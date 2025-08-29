package com.sm.products.core.utils.networkMonitor

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}