package com.stebitto.feature_user_repos.impl.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stebitto.feature_user_repos.impl.models.UserRepoDBEntity

@Dao
internal interface UserRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<UserRepoDBEntity>)

    @Query("SELECT * FROM user_repos WHERE name = :name")
    suspend fun getRepoByName(name: String): UserRepoDBEntity?

    @Query("SELECT * FROM user_repos")
    suspend fun getAllRepos(): List<UserRepoDBEntity>

    @Query("DELETE FROM user_repos")
    suspend fun deleteAllRepos()
}