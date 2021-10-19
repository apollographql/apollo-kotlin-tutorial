package com.example.rocketreserver

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.http.httpHeader
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import com.apollographql.apollo3.network.ws.WebSocketNetworkTransport
import kotlinx.coroutines.flow.Flow

private var instance: ApolloClient? = null

fun apolloClient(context: Context): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    instance = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .subscriptionNetworkTransport(WebSocketNetworkTransport("wss://apollo-fullstack-tutorial.herokuapp.com/graphql"))
        .addInterceptor(AuthorizationInterceptor(context))
        .build()

    return instance!!
}

private class AuthorizationInterceptor(val context: Context) : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val requestWithHeader = request.newBuilder()
            .httpHeader("Authorization", User.getToken(context) ?: "")
            .build()
        return chain.proceed(requestWithHeader)
    }
}
