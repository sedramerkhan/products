package com.sm.products.core.di

import android.content.Context
import android.util.Log

import com.sm.products.core.BaseApplication
import com.sm.products.core.networkMonitor.NetworkMonitor
import com.sm.products.data.remote.ProductApi
import com.sm.products.data.repository.ProductRepository
import com.sm.products.domain.repository.IProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val customInterceptor = Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            val rawJson = response.body?.string()

            Log.i("customInterceptor", "${response.request.url}")
            Log.i("customInterceptor", "${response.isSuccessful}")
            Log.i("customInterceptor", "$rawJson")
            // Recreate the response body after reading it
            response.newBuilder()
                .body((rawJson ?: "").toResponseBody(response.body?.contentType()))
                .build()
        }

        return OkHttpClient.Builder()
            .addInterceptor(customInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi, networkChecker: NetworkMonitor) : IProductRepository =
        ProductRepository(api, networkChecker)


}