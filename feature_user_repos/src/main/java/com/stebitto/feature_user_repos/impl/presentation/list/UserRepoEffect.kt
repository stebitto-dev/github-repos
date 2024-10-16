package com.stebitto.feature_user_repos.impl.presentation.list

import com.stebitto.common.api.Effect

internal sealed class UserRepoEffect : Effect {
    data class NavigateToRepoDetail(val owner: String, val repoName: String) : UserRepoEffect()
}