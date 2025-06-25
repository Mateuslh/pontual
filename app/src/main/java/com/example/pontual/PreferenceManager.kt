package com.example.pontual

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREF_NAME = "PontualPrefs"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_PASSWORD = "user_password"
    private const val KEY_TOKEN = "user_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_ROLE = "user_role"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserCredentials(context: Context, email: String, password: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun saveUserData(context: Context, token: String, userId: Int, userName: String, userRole: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putInt(KEY_USER_ID, userId)
            .putString(KEY_USER_NAME, userName)
            .putString(KEY_USER_ROLE, userRole)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun getUserEmail(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_EMAIL, null)
    }

    fun getUserPassword(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_PASSWORD, null)
    }

    fun getToken(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_TOKEN, null)
    }

    fun getUserId(context: Context): Int {
        val prefs = getSharedPreferences(context)
        return prefs.getInt(KEY_USER_ID, -1)
    }

    fun getUserName(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_USER_NAME, null)
    }

    fun getUserRole(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_USER_ROLE, null)
    }

    fun isLoggedIn(context: Context): Boolean {
        val prefs = getSharedPreferences(context)
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun isAdmin(context: Context): Boolean {
        val role = getUserRole(context)
        return role == "admin"
    }

    fun clearUserData(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().clear().apply()
    }

    fun logout(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .remove(KEY_TOKEN)
            .remove(KEY_USER_ID)
            .remove(KEY_USER_NAME)
            .remove(KEY_USER_ROLE)
            .apply()
    }
} 