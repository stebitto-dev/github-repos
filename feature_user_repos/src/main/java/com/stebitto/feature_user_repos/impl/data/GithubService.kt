package com.stebitto.feature_user_repos.impl.data

import com.stebitto.feature_user_repos.impl.models.UserRepoRemote
import retrofit2.http.GET
import retrofit2.http.Header

internal interface GitHubService {
    @GET("user/repos")
    suspend fun getUserRepos(@Header("Authorization") authHeader: String): List<UserRepoRemote>
}