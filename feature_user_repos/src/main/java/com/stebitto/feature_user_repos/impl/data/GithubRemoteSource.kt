package com.stebitto.feature_user_repos.impl.data

import com.stebitto.feature_user_repos.impl.models.UserRepoRemote

internal interface GithubRemoteSource {
    suspend fun getUserRepos(token: String): List<UserRepoRemote>
}