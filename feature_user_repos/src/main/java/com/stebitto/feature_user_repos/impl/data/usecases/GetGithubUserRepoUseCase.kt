package com.stebitto.feature_user_repos.impl.data.usecases

import com.stebitto.common.api.models.UserRepoDTO

interface GetGithubUserRepoUseCase {
    suspend operator fun invoke(repoName: String): Result<UserRepoDTO?>
}