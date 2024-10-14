package com.stebitto.feature_user_repos.impl.data

import com.stebitto.common.api.GetGithubTokenUseCase
import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.api.GithubUserRepoUseCase

internal class GithubUserRepoUseCaseImpl(
    private val githubRepository: GithubRepository,
    private val getGithubTokenUseCase: GetGithubTokenUseCase
) : GithubUserRepoUseCase {
    override suspend fun invoke(): Result<List<UserRepoDTO>> {
        return getGithubTokenUseCase().fold(
            onSuccess = { token ->
                githubRepository.getUserRepos(token)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}