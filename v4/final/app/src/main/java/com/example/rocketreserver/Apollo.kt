package com.example.rocketreserver

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

private class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                TokenRepository.getToken()?.let { token ->
                    addHeader("Authorization", token)
                }
            }
            .build()
        return chain.proceed(request)
    }
}

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
    .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
    .okHttpClient(
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .build()
    )
    .webSocketReopenWhen { throwable, attempt ->
        Log.d("Apollo", "WebSocket got disconnected, reopening after a delay", throwable)
        delay(attempt * 1000)
        true
    }

    .build()
