package com.stebitto.feature_user_repos.impl.data

import com.stebitto.feature_user_repos.impl.models.UserRepoDBEntity

internal interface GithubLocalSource {
    suspend fun saveUserRepos(repos: List<UserRepoDBEntity>)
    suspend fun getUserRepoById(id: Int): UserRepoDBEntity?
    suspend fun getUserRepos(): List<UserRepoDBEntity>
    suspend fun clearUserRepos()
}