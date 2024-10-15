package com.stebitto.feature_user_repos.impl.data.usecases

import com.stebitto.common.api.UserRepository
import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.feature_user_repos.api.GithubRepository

class GetGithubUserRepoUseCaseImpl(
    private val userRepository: UserRepository,
    private val githubRepository: GithubRepository
) : GetGithubUserRepoUseCase {
    override suspend fun invoke(repoName: String): Result<UserRepoDTO?> {
        return userRepository.getUserName().fold(
            onSuccess = { name ->
                githubRepository.getUserRepoByName(name, repoName)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}