package com.stebitto.feature_user_repos.impl.presentation

import com.stebitto.common.api.Intent

internal sealed class UserRepoIntent : Intent {
    data object FetchRepos : UserRepoIntent()
}
