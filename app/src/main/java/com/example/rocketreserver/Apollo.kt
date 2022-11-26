package com.example.rocketreserver

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.http.*
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.File

private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor(context))
        .build()

    //val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

    instance = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .okHttpClient(okHttpClient)
        .httpCache(
            directory = File(context.cacheDir.absolutePath, "apolloCache"),
            maxSize = 50 * 1024 * 1024
        )
        .httpExpireTimeout(1000 * 1000) // can be updated
        .addHttpInterceptor(CachingHttpInterceptor(
            directory = File(context.cacheDir.absolutePath, "apolloCache"),
            maxSize = 50 * 1024 * 1024
        ))
        .build()

    return instance!!
}

private class AuthorizationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", User.getToken(context) ?: "")
            .build()

        return chain.proceed(request)
    }
}
