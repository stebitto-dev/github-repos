package com.stebitto.feature_user_repos.api

import com.stebitto.common.api.models.UserRepoDTO

interface GithubRepository {
    suspend fun getUserRepos(token: String): Result<List<UserRepoDTO>>
    suspend fun getUserRepoById(id: Int): Result<UserRepoDTO?>
    suspend fun saveUserRepos(repos: List<UserRepoDTO>)
    suspend fun clearUserRepos()
}