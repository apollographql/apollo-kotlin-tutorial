package com.example.rocketreserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.example.rocketserver.LaunchListQuery

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val apolloClient = ApolloClient.builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
            .build()

        lifecycleScope.launchWhenResumed {
            val response = try {
                apolloClient.query(LaunchListQuery()).toDeferred().await()
            } catch (e: ApolloException) {
                Log.d("LaunchList", "Failure", e)
                return@launchWhenResumed
            }

            val launches = response.data?.launches?.launches?.filterNotNull()

            if (launches != null) {
                val recyclerView = findViewById<RecyclerView>(R.id.launches)
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = LaunchListAdapter(launches)
            }
        }
    }
}
