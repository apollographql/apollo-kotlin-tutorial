package com.example.rocketreserver

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.apollographql.apollo3.cache.normalized.watch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LaunchList() }
    }
}

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
    Row(){AsyncImage(model = launch.mission?.missionPatch, contentDescription = null, contentScale = ContentScale.Fit )
        Column() {
            Text(
                text = launch.mission?.name ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            )
            Text(
                text = launch.site ?: "",
                fontWeight = FontWeight.Light,
                fontSize = 22.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 12.dp)
            )
        }
      }
    // button for booking a trip
    // fix the sizing issue o the
}