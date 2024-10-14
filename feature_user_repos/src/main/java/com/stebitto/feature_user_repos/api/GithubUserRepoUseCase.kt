package com.stebitto.feature_user_repos.api

import com.stebitto.common.api.models.UserRepoDTO

interface GithubUserRepoUseCase {
    suspend operator fun invoke(): Result<List<UserRepoDTO>>
}