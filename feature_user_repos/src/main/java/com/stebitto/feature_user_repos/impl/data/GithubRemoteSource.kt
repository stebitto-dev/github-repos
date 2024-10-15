package com.stebitto.feature_user_repos.impl.data

import com.stebitto.feature_user_repos.impl.models.UserRepoRemote

internal interface GithubRemoteSource {
    suspend fun getUserRepos(): List<UserRepoRemote>
    suspend fun getRepo(owner: String, repoName: String): UserRepoRemote
    suspend fun starRepo(owner: String, repoName: String)
}