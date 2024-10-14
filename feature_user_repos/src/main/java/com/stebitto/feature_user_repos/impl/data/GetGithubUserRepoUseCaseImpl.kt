package com.stebitto.feature_user_repos.impl.data

import com.stebitto.common.api.GetGithubTokenUseCase
import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.api.GetGithubUserRepoUseCase

internal class GetGithubUserRepoUseCaseImpl(
    private val githubRepository: GithubRepository,
    private val getGithubTokenUseCase: GetGithubTokenUseCase
) : GetGithubUserRepoUseCase {
    override suspend fun invoke(): Result<List<UserRepoDTO>> {
        return getGithubTokenUseCase().fold(
            onSuccess = { token ->
                githubRepository.getUserRepos(token).apply {
                    onSuccess {
                        githubRepository.clearUserRepos()
                        githubRepository.saveUserRepos(it)
                    }
                }
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}