package com.stebitto.feature_user_repos.impl.data

import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.feature_user_repos.api.GithubRepository

internal class GithubRepositoryImpl(private val githubRemoteSource: GithubRemoteSource) : GithubRepository {
    override suspend fun getUserRepos(token: String): Result<List<UserRepoDTO>> = runCatching {
        val response = githubRemoteSource.getUserRepos(token)
        return Result.success(
            response
                .filter { it.id != null } // in case there was an error retrieving id
                .map { it.toUserRepoDTO() }
        )
    }
}