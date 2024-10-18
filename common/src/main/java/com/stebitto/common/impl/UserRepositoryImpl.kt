package com.stebitto.common.impl

import android.content.Context
import com.stebitto.common.api.UserRepository

internal class UserRepositoryImpl(
    context: Context
): UserRepository {
    private companion object {
        const val GITHUB_PREFERENCES_NAME = "github_preferences"
        const val GITHUB_TOKEN_KEY = "github_token"
    }

    private val sharedPreferences =
        context.getSharedPreferences(GITHUB_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun getGithubToken(): Result<String> {
        return sharedPreferences.getString(GITHUB_TOKEN_KEY, null)?.let { token ->
            Result.success(token)
        } ?: Result.failure(Exception("No token found"))
    }

    override fun saveGithubToken(token: String) {
        sharedPreferences.edit().putString(GITHUB_TOKEN_KEY, token).apply()
    }

    override fun clearGithubToken() {
        sharedPreferences.edit().remove(GITHUB_TOKEN_KEY).apply()
    }
}