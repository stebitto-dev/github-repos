package com.stebitto.common.api.models

data class UserRepoDetailDTO(
    val id: Int,
    val name: String,
    val owner: String,
    val description: String,
    val language: String,
    val private: Boolean,
    val htmlUrl: String,
    val numberOfStars: Int,
    val numberOfWatchers: Int,
    val numberOfIssues: Int,
    val isStarred: Boolean
)