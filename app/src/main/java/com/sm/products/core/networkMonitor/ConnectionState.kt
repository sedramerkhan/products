package com.sm.products.core.networkMonitor

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}