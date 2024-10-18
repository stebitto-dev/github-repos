package com.stebitto.feature_user_repos.impl.data.usecases

import com.stebitto.common.api.models.UserRepoDetailDTO
import com.stebitto.feature_user_repos.api.GithubRepository

internal class StarGithubRepoUseCaseImpl(
    private val githubRepository: GithubRepository
) : StarGithubRepoUseCase {
    override suspend fun invoke(owner: String, repoName: String): Result<UserRepoDetailDTO?> = runCatching {
        return githubRepository.starRepo(owner, repoName).fold(
            onSuccess = {
                githubRepository.getUserRepoByName(owner, repoName)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}