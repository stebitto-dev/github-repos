package com.stebitto.feature_user_repos.impl.presentation.list

import com.stebitto.common.api.Intent

internal sealed class UserRepoIntent : Intent {
    data object FetchRepos : UserRepoIntent()
    data class LoadLastVisitedRepo(val owner: String, val repoName: String) : UserRepoIntent()
    data class RepoClicked(val owner: String, val repoName: String) : UserRepoIntent()
    data object SignOut : UserRepoIntent()
}
