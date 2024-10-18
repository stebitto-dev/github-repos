package com.stebitto.feature_user_repos.impl.models

import com.stebitto.common.api.models.UserRepoDetailDTO

internal data class UserRepoDetailRemote(
    val id: Int?,
    val name: String?,
    val full_name: String?,
    val description: String?,
    val language: String?,
    val private: Boolean?,
    val html_url: String?,
    val stargazers_count: Int?,
    val watchers_count: Int?,
    val open_issues_count: Int?
) {
    fun toUserRepoDetailDTO(isStarred: Boolean): UserRepoDetailDTO {
        return UserRepoDetailDTO(
            id = id ?: -1,
            name = name ?: "",
            owner = full_name?.split("/")?.get(0) ?: "",
            description = description ?: "",
            language = language ?: "",
            private = private ?: false,
            htmlUrl = html_url ?: "",
            numberOfStars = stargazers_count ?: 0,
            numberOfWatchers = watchers_count ?: 0,
            numberOfIssues = open_issues_count ?: 0,
            isStarred = isStarred
        )
    }
}
