package com.stebitto.feature_user_repos.impl.data

import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.impl.models.toUserRepoDBEntity

internal class GithubRepositoryImpl(
    private val githubRemoteSource: GithubRemoteSource,
    private val githubLocalSource: GithubLocalSource
) : GithubRepository {

    override suspend fun getUserRepos(token: String): Result<List<UserRepoDTO>> = runCatching {
        val response = githubRemoteSource.getUserRepos(token)
        return Result.success(
            response
                .filter { it.id != null } // in case there was an error retrieving id
                .map { it.toUserRepoDTO() }
        )
    }

    override suspend fun getUserRepoById(id: Int): Result<UserRepoDTO?> = runCatching {
        val response = githubLocalSource.getUserRepoById(id)
        return Result.success(response?.toUserRepoDTO())
    }

    override suspend fun saveUserRepos(repos: List<UserRepoDTO>) {
        githubLocalSource.saveUserRepos(repos.map { it.toUserRepoDBEntity() })
    }

    override suspend fun clearUserRepos() {
        githubLocalSource.clearUserRepos()
    }
}