package com.stebitto.feature_user_repos.impl.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class GetGithubTokenUseCaseImpl : GetGithubTokenUseCase {
    override suspend fun invoke(): Result<String> {
        FirebaseAuth.getInstance().currentUser?.let { currentUser ->
            return try {
                currentUser.getIdToken(false).await().token?.let { token ->
                    Result.success(token)
                } ?: Result.failure(Exception("Token not found"))
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        } ?: return Result.failure(Exception("User not authenticated, logout and login again"))
    }
}