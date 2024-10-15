package com.stebitto.feature_user_repos.impl.data.usecases

import com.stebitto.common.api.models.UserRepoDTO

interface GetGithubUserRepoListUseCase {
    suspend operator fun invoke(): Result<List<UserRepoDTO>>
}