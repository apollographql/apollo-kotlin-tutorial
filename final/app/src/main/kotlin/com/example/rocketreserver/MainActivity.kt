@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rocketreserver

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.cache.normalized.isFromCache
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.watch
import com.example.rocketreserver.ui.theme.RocketReserverTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            doStuff(this@MainActivity)
        }

        TokenRepository.init(this)
        setContent {
            RocketReserverTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val tripBookedFlow = remember { apolloClient.subscription(TripsBookedSubscription()).toFlow() }
                val tripBookedResponse: ApolloResponse<TripsBookedSubscription.Data>? by tripBookedFlow.collectAsState(initial = null)
                LaunchedEffect(tripBookedResponse) {
                    if (tripBookedResponse == null) return@LaunchedEffect
                    val message = when (tripBookedResponse!!.data?.tripsBooked) {
                        null -> "Subscription error"
                        -1 -> "Trip cancelled"
                        else -> "Trip booked! 🚀"
                    }
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }

                Scaffold(
                    topBar = { TopAppBar({ Text(stringResource(R.string.app_name)) }) },
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) { paddingValues ->
                    Box(Modifier.padding(paddingValues)) {
                        MainNavHost()
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationDestinations.LAUNCH_LIST) {
        composable(route = NavigationDestinations.LAUNCH_LIST) {
            LaunchList(
                onLaunchClick = { launchId ->
                    navController.navigate("${NavigationDestinations.LAUNCH_DETAILS}/$launchId")
                }
            )
        }

        composable(route = "${NavigationDestinations.LAUNCH_DETAILS}/{${NavigationArguments.LAUNCH_ID}}") { navBackStackEntry ->
            LaunchDetails(
                launchId = navBackStackEntry.arguments!!.getString(NavigationArguments.LAUNCH_ID)!!,
                navigateToLogin = {
                    navController.navigate(NavigationDestinations.LOGIN)
                }
            )
        }

        composable(route = NavigationDestinations.LOGIN) {
            Login(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

private suspend fun doStuff(context: Context) {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .normalizedCache(MemoryCacheFactory().chain(SqlNormalizedCacheFactory(context)))
        .build()

    // warm the cache. Only do this once and then comment it
//    apolloClient.query(LaunchListQuery()).fetchPolicy(FetchPolicy.CacheFirst).execute().apply {
//        check(data != null)
//        check(!isFromCache)
//    }

    withTimeout(1000) {
        apolloClient.query(LaunchListQuery())
            .fetchPolicy(FetchPolicy.CacheAndNetwork)
            .watch()
            .first()
            .apply {
                check(data != null)
                check(isFromCache)
            }
    }
    println("all good")
}
