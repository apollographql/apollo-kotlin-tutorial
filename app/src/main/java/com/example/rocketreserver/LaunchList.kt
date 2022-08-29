package com.example.rocketreserver

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.apollographql.apollo3.cache.normalized.watch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text

sealed class UiState {
    object Loading : UiState()
    object Error : UiState()
    class Success(val launchList: List<LaunchListBlogQuery.Launch>) : UiState()
}

@Composable
fun LaunchList() {

    val context = LocalContext.current
    // tell Compose to remember our state across recompositions
    val state = remember {
        apolloClient(context).query(LaunchListBlogQuery()).watch()
            .map {
                val launchList = it
                    .data
                    ?.launchConnection
                    ?.launches
                    ?.filterNotNull()
                if (launchList == null) {
                    // There were some error
                    // TODO: do something with response.errors
                    UiState.Error
                } else {
                    UiState.Success(launchList)
                }
            }
            .catch { e ->
                emit(UiState.Error)
            }
    }
// collectAsState will turn our flow into State that can be consumed by Composables
        .collectAsState(initial = UiState.Loading)

// Display the com.example.rocketreserver.UiState as usual
    when (val value = state.value) {
        is UiState.Success -> LazyColumn(content = {
            items(value.launchList) {
                LaunchItem(it)
            }
        })
        else -> {}
    }
}

@Composable
fun LaunchItem(launch: LaunchListBlogQuery.Launch) {
    Text(text = launch.mission?.name ?: "")
}