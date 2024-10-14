package com.stebitto.common.impl

import android.content.Context
import com.stebitto.common.api.SaveGithubTokenUseCase

class SaveGithubTokenUseCaseImpl(
    private val context: Context
) : SaveGithubTokenUseCase {
    override suspend fun invoke(token: String) {
        val sharedPreferences = context.getSharedPreferences("github_preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("github_token", token).apply()
    }
}