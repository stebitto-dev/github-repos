package com.stebitto.feature_user_repos.impl.presentation.detail

import com.stebitto.common.api.Intent

internal sealed class UserRepoDetailIntent : Intent {
    data class LoadUserRepo(val id: Int) : UserRepoDetailIntent()
}