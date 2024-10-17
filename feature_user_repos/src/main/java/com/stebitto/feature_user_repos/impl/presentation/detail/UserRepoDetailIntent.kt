package com.stebitto.feature_user_repos.impl.presentation.detail

import com.stebitto.common.api.Intent

internal sealed class UserRepoDetailIntent : Intent {
    data class LoadUserRepo(val owner: String, val repoName: String) : UserRepoDetailIntent()
    data class StarClicked(val owner: String, val repoName: String, val isStarred: Boolean) : UserRepoDetailIntent()
    data object SignOut : UserRepoDetailIntent()
}