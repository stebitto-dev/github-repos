package com.stebitto.feature_user_repos.api

import com.stebitto.common.api.models.UserRepoDTO

interface GithubRepository {
    suspend fun getUserRepos(): Result<List<UserRepoDTO>>
    suspend fun getUserRepoByName(owner: String, name: String): Result<UserRepoDTO?>
    suspend fun saveUserRepos(repos: List<UserRepoDTO>)
    suspend fun clearUserRepos()
    suspend fun starRepo(owner: String, repoName: String): Result<Unit>
}