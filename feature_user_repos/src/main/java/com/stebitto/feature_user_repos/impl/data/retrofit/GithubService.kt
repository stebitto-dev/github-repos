package com.stebitto.feature_user_repos.impl.data.retrofit

import com.stebitto.feature_user_repos.impl.models.UserRepoRemote
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface GitHubService {
    @GET("user/repos")
    suspend fun getUserRepos(): List<UserRepoRemote>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): UserRepoRemote

    @PUT("user/starred/{owner}/{repo}")
    suspend fun starRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    )
}