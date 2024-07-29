package com.example.rocketreserver

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object TokenRepository {
    private const val KEY_TOKEN = "TOKEN"

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        preferences = EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    fun getToken(): String? {
        return preferences.getString(KEY_TOKEN, null)
    }

    fun setToken(token: String) {
        preferences.edit().apply {
            putString(KEY_TOKEN, token)
            apply()
        }
    }

    fun removeToken() {
        preferences.edit().apply {
            remove(KEY_TOKEN)
            apply()
        }
    }
}
