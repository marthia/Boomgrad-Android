package me.marthia.app.boomgrad.data.local

import android.content.Context
import androidx.core.content.edit

/**
 * Manages the storage and retrieval of authentication tokens.
 *
 * This class provides a simple interface to securely store, retrieve, and clear
 * authentication tokens using Android's SharedPreferences.
 *
 * @property context The application context, used to access SharedPreferences.
 */
class TokenManager(private val context: Context) {
    private val prefs =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit { putString("auth_token", token) }
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearToken() {
        prefs.edit { remove("auth_token") }
    }
}