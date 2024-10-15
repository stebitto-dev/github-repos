package com.stebitto.feature_user_repos.impl.data

import com.stebitto.feature_user_repos.impl.data.room.AppDatabase
import com.stebitto.feature_user_repos.impl.models.UserRepoDBEntity

internal class GithubLocalSourceImpl(
    private val appDatabase: AppDatabase
) : GithubLocalSource {
    override suspend fun saveUserRepos(repos: List<UserRepoDBEntity>) {
        appDatabase.userRepoDao().insertAll(repos)
    }

    override suspend fun getUserRepoByName(name: String): UserRepoDBEntity? {
        return appDatabase.userRepoDao().getRepoByName(name)
    }

    override suspend fun getUserRepos(): List<UserRepoDBEntity> {
        return appDatabase.userRepoDao().getAllRepos()
    }

    override suspend fun clearUserRepos() {
        appDatabase.userRepoDao().deleteAllRepos()
    }
}