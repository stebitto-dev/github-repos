package com.stebitto.feature_user_repos.impl.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stebitto.feature_user_repos.impl.models.UserRepoDBEntity

@Database(entities = [UserRepoDBEntity::class], version = 1, exportSchema = false)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userRepoDao(): UserRepoDao
}