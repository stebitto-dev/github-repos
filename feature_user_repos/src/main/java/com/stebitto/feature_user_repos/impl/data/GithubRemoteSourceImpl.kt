package com.stebitto.feature_user_repos.impl.data

import com.stebitto.feature_user_repos.impl.data.retrofit.GitHubService
import com.stebitto.feature_user_repos.impl.models.UserRepoRemote

internal class GithubRemoteSourceImpl(private val githubService: GitHubService) : GithubRemoteSource {

    override suspend fun getUserRepos(): List<UserRepoRemote> {
        return githubService.getUserRepos()
    }

    override suspend fun getRepo(owner: String, repoName: String): UserRepoRemote {
        return githubService.getRepo(owner, repoName)
    }

    override suspend fun starRepo(owner: String, repoName: String) {
        return githubService.starRepo(owner, repoName)
    }
}