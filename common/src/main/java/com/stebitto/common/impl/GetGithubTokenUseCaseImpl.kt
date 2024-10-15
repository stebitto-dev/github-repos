package com.stebitto.common.impl

import android.content.Context
import com.stebitto.common.api.GetGithubTokenUseCase

class GetGithubTokenUseCaseImpl(
    private val context: Context
) : GetGithubTokenUseCase {
    override fun invoke(): Result<String> {
        val sharedPreferences = context.getSharedPreferences("github_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("github_token", null)?.let { token ->
            Result.success(token)
        } ?: Result.failure(Exception("No token found"))
    }
}