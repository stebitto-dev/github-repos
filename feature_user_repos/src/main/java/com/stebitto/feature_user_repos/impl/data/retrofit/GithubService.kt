package com.stebitto.feature_user_repos.impl.data.retrofit

import com.stebitto.feature_user_repos.impl.models.UserRepoDetailRemote
import com.stebitto.feature_user_repos.impl.models.UserRepoRemote
import retrofit2.Response
import retrofit2.http.DELETE
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
    ): UserRepoDetailRemote

    @GET("user/starred/{owner}/{repo}")
    suspend fun checkIfRepoStarred(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

    @PUT("user/starred/{owner}/{repo}")
    suspend fun starRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

    @DELETE("user/starred/{owner}/{repo}")
    suspend fun unstarRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>
}