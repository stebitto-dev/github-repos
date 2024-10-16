package com.stebitto.feature_user_repos.impl.data

import com.stebitto.feature_user_repos.impl.data.retrofit.GitHubService
import com.stebitto.feature_user_repos.impl.models.UserRepoDetailRemote
import com.stebitto.feature_user_repos.impl.models.UserRepoRemote

internal class GithubRemoteSourceImpl(private val githubService: GitHubService) : GithubRemoteSource {

    override suspend fun getUserRepos(): List<UserRepoRemote> {
        return githubService.getUserRepos()
    }

    override suspend fun getRepo(owner: String, repoName: String): UserRepoDetailRemote {
        return githubService.getRepo(owner, repoName)
    }

    override suspend fun checkIfRepoStarred(owner: String, repoName: String): Boolean {
        return githubService.checkIfRepoStarred(owner, repoName).isSuccessful
    }

    override suspend fun starRepo(owner: String, repoName: String) {
        githubService.starRepo(owner, repoName)
    }

    override suspend fun unstarRepo(owner: String, repoName: String) {
        githubService.unstarRepo(owner, repoName)
    }
}