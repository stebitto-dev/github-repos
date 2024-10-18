package com.stebitto.feature_user_repos.impl.data.usecases

import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.feature_user_repos.api.GithubRepository

internal class GetGithubUserRepoListUseCaseImpl(
    private val githubRepository: GithubRepository
) : GetGithubUserRepoListUseCase {
    override suspend fun invoke(): Result<List<UserRepoDTO>> {
        return githubRepository.getUserRepos()
            .onSuccess {
                githubRepository.clearUserRepos()
                githubRepository.saveUserRepos(it)
            }
    }
}