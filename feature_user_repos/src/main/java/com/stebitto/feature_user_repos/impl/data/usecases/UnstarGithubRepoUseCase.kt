package com.stebitto.feature_user_repos.impl.data.usecases

import com.stebitto.common.api.models.UserRepoDetailDTO

internal interface UnstarGithubRepoUseCase {
    suspend operator fun invoke(owner: String, repoName: String): Result<UserRepoDetailDTO?>
}