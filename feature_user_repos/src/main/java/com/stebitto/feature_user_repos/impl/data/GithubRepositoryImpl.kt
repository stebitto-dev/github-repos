package com.stebitto.feature_user_repos.impl.data

import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.impl.models.toUserRepoDBEntity

internal class GithubRepositoryImpl(
    private val githubRemoteSource: GithubRemoteSource,
    private val githubLocalSource: GithubLocalSource
) : GithubRepository {

    override suspend fun getUserRepos(): Result<List<UserRepoDTO>> {
        try {
            val response = githubRemoteSource.getUserRepos()
            return Result.success(
                response
                    .filter { it.id != null } // in case there was an error retrieving id
                    .map { it.toUserRepoDTO() }
            )
        } catch (e: Exception) {
            // If there is a problem with web service, get data from local db
            val response = githubLocalSource.getUserRepos()
            return Result.success(response.map { it.toUserRepoDTO() })
        }
    }

    override suspend fun getUserRepoByName(owner: String, name: String): Result<UserRepoDTO?> {
        try {
            val response = githubRemoteSource.getRepo(owner, name)
            return Result.success(response.toUserRepoDTO())
        } catch (e: Exception) {
            // If there is a problem with web service, get data from local db
            val response = githubLocalSource.getUserRepoByName(name)
            return Result.success(response?.toUserRepoDTO())
        }
    }

    override suspend fun saveUserRepos(repos: List<UserRepoDTO>) {
        githubLocalSource.saveUserRepos(repos.map { it.toUserRepoDBEntity() })
    }

    override suspend fun clearUserRepos() {
        githubLocalSource.clearUserRepos()
    }

    override suspend fun starRepo(owner: String, repoName: String): Result<Unit> = runCatching {
        githubRemoteSource.starRepo(owner, repoName)
    }
}