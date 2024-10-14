package com.stebitto.feature_user_repos.impl.data

interface GetGithubTokenUseCase {
    suspend operator fun invoke(): Result<String>
}