package com.stebitto.feature_user_repos.impl.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stebitto.common.api.models.UserRepoDTO

@Entity(tableName = "user_repos")
internal data class UserRepoDBEntity(
    val id: Int,
    @PrimaryKey val name: String,
    val owner : String,
    val description: String,
    val language: String,
    val numberOfStars: Int
) {
    fun toUserRepoDTO(): UserRepoDTO {
        return UserRepoDTO(
            id = id,
            name = name,
            owner = owner,
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
        owner = owner,
        description = description,
        language = language,
        numberOfStars = numberOfStars
    )
}
