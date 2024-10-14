package com.stebitto.feature_user_repos.impl.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stebitto.common.api.models.UserRepoDTO

@Entity(tableName = "user_repos")
internal data class UserRepoDBEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val language: String,
    val numberOfStars: Int
) {
    fun toUserRepoDTO(): UserRepoDTO {
        return UserRepoDTO(
            id = id,
            name = name,
            description = description,
            language = language,
            numberOfStars = numberOfStars
        )
    }
}

internal fun UserRepoDTO.toUserRepoDBEntity(): UserRepoDBEntity {
    return UserRepoDBEntity(
        id = id,
        name = name,
        description = description,
        language = language,
        numberOfStars = numberOfStars
    )
}
