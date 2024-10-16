package com.stebitto.feature_user_repos.impl.models

import com.stebitto.common.api.models.UserRepoDetailDTO

internal data class UserRepoDetailPresentation(
    val id: Int,
    val name: String,
    val owner: String,
    val description: String,
    val language: String,
    val private: Boolean,
    val htmlUrl: String,
    val numberOfStars: Int,
    val numberOfWatchers: Int,
    val numberOfIssues: Int
)

internal fun UserRepoDetailDTO.toDetailPresentationModel(): UserRepoDetailPresentation {
    return UserRepoDetailPresentation(
        id = id,
        name = name,
        owner = owner,
        description = description,
        language = language,
        numberOfStars = numberOfStars,
        numberOfWatchers = numberOfWatchers,
        numberOfIssues = numberOfIssues,
        private = private,
        htmlUrl = htmlUrl
    )
}