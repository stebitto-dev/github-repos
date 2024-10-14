package com.stebitto.feature_user_repos.api

import com.stebitto.common.api.models.UserRepoDTO

interface GetGithubUserRepoUseCase {
    suspend operator fun invoke(): Result<List<UserRepoDTO>>
}