package com.sm.products.core.data

import android.util.Log
import com.sm.products.core.domain.DataError
import com.sm.products.core.domain.Result
import com.sm.products.core.networkMonitor.NetworkMonitor
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeCall(
    networkChecker: NetworkMonitor,
    crossinline execute: suspend () -> Response<T>
): Result<T, DataError.Remote> {
    return try {

        Log.i("Custom","safeCall ${networkChecker.isConnected()}")
        if (!networkChecker.isConnected()) {
            return Result.Error(DataError.Remote.NO_INTERNET)
        }
        val response = execute()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        } else {
            when (response.code()) {
                408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
                429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
                in 500..599 -> Result.Error(DataError.Remote.SERVER)
                else -> Result.Error(DataError.Remote.UNKNOWN)
            }
        }
    } catch (e: SocketTimeoutException) {
        Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnknownHostException) {
        Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: CancellationException) {
        throw e // propagate coroutine cancellation
    } catch (e: Exception) {
        Result.Error(DataError.Remote.UNKNOWN)
    }
}
