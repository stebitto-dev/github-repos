package com.stebitto.feature_user_repos.api

import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.common.api.models.UserRepoDetailDTO

interface GithubRepository {
    suspend fun getUserRepos(): Result<List<UserRepoDTO>>
    suspend fun getUserRepoByName(owner: String, name: String): Result<UserRepoDetailDTO?>
    suspend fun saveUserRepos(repos: List<UserRepoDTO>)
    suspend fun clearUserRepos()
    suspend fun starRepo(owner: String, repoName: String): Result<Unit>
    suspend fun unstarRepo(owner: String, repoName: String): Result<Unit>
}