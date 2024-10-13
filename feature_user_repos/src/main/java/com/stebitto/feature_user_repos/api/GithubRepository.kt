package com.stebitto.feature_user_repos.api

import com.stebitto.common.api.models.UserRepoDTO

interface GithubRepository {
    suspend fun getUserRepos(token: String): Result<List<UserRepoDTO>>
}