@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rocketreserver

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apollographql.apollo3.exception.ApolloException
import kotlinx.coroutines.launch

@Composable
fun Login(navigateBack: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Title
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            text = "Login"
        )

        // Email
        var email by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            value = email,
            onValueChange = { email = it }
        )

        // Submit button
        var loading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        Button(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            enabled = !loading,
            onClick = {
                loading = true
                scope.launch {
                    val ok = login(email)
                    loading = false
                    if (ok) navigateBack()
                }
            }
        ) {
            if (loading) {
                Loading()
            } else {
                Text(text = "Submit")
            }
        }
    }
}

private suspend fun login(email: String): Boolean {
    val response = try {
        apolloClient.mutation(LoginMutation(email = email)).execute()
    } catch (e: ApolloException) {
        Log.w("Login", "Failed to login", e)
        return false
    }
    if (response.hasErrors()) {
        Log.w("Login", "Failed to login: ${response.errors?.get(0)?.message}")
        return false
    }
    val token = response.data?.login?.token
    if (token == null) {
        Log.w("Login", "Failed to login: no token returned by the backend")
        return false
    }
    TokenRepository.setToken(token)
    return true
}

@Composable
private fun Loading() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = LocalContentColor.current,
        strokeWidth = 2.dp,
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    Login(navigateBack = { })
}
