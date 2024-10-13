package com.stebitto.feature_user_repos.impl.data

import com.stebitto.feature_user_repos.impl.models.UserRepoRemote

internal class GithubRemoteSourceImpl(private val githubService: GitHubService) : GithubRemoteSource {
    override suspend fun getUserRepos(token: String): List<UserRepoRemote> {
        val authHeader = "Bearer $token"
        return githubService.getUserRepos(authHeader)
    }
}