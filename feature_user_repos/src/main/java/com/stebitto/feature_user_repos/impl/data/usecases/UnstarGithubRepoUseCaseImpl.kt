package com.stebitto.feature_user_repos.impl.data.usecases

import com.stebitto.common.api.models.UserRepoDetailDTO
import com.stebitto.feature_user_repos.api.GithubRepository

internal class UnstarGithubRepoUseCaseImpl(
    private val githubRepository: GithubRepository
) : UnstarGithubRepoUseCase {
    override suspend fun invoke(owner: String, repoName: String): Result<UserRepoDetailDTO?> = runCatching {
        return githubRepository.unstarRepo(owner, repoName).fold(
            onSuccess = {
                githubRepository.getUserRepoByName(owner, repoName)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}