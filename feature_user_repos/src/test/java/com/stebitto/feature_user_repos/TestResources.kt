package com.stebitto.feature_user_repos

import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.common.api.models.UserRepoDetailDTO
import com.stebitto.feature_user_repos.impl.models.UserRepoDBEntity
import com.stebitto.feature_user_repos.impl.models.UserRepoDetailPresentation
import com.stebitto.feature_user_repos.impl.models.UserRepoDetailRemote
import com.stebitto.feature_user_repos.impl.models.UserRepoPresentation
import com.stebitto.feature_user_repos.impl.models.UserRepoRemote

internal val userRepoDTO1 = UserRepoDTO(1, "Repo 1", "me", "Description 1", "Kotlin", 2)
internal val userRepoDTO2 = UserRepoDTO(2, "Repo 2", "you", "Description 2", "Java", -1)
internal val userRepoDTO3 = UserRepoDTO(3, "Repo 3", "me", "Description 3", "Python", 300)
internal val userRepoDTO4 = UserRepoDTO(4, "Repo 4", "octocat", "Description 4", "C++", 0)

internal val userRepoRemote1 = UserRepoRemote(1, "Repo 1", "me/Repo 1", "Description 1", "Kotlin", 2)
internal val userRepoRemote2 = UserRepoRemote(2, "Repo 2", "you/Repo 2", "Description 2", "Java", -1)
internal val userRepoRemote3 = UserRepoRemote(3, "Repo 3", "me/Repo 3", "Description 3", "Python", 300)
internal val userRepoRemote4 = UserRepoRemote(4, "Repo 4", "octocat/Repo 4", "Description 4", "C++", 0)
internal val userRepoRemote5 = UserRepoRemote(null, "Repo 5", "test/Repo 5", "Description 5", "C++", 0)

internal val userRepoDBEntity1 = UserRepoDBEntity(1, "Repo 1", "me", "Description 1", "Kotlin", 2)
internal val userRepoDBEntity2 = UserRepoDBEntity(2, "Repo 2", "you", "Description 2", "Java", -1)
internal val userRepoDBEntity3 = UserRepoDBEntity(3, "Repo 3", "me", "Description 3", "Python", 300)
internal val userRepoDBEntity4 = UserRepoDBEntity(4, "Repo 4", "octocat", "Description 4", "C++", 0)

internal val userRepoPresentation1 = UserRepoPresentation(1, "Repo 1", "me", "Description 1", "Kotlin", 2)
internal val userRepoPresentation2 = UserRepoPresentation(2, "Repo 2", "you", "Description 2", "Java", -1)
internal val userRepoPresentation3 = UserRepoPresentation(3, "Repo 3", "me", "Description 3", "Python", 300)
internal val userRepoPresentation4 = UserRepoPresentation(4, "Repo 4", "octocat", "Description 4", "C++", 0)

internal val userRepoDetailDTO = UserRepoDetailDTO(1, "Repo 1", "octocat", "Description 1", "Kotlin", false, "", 2, 1, 0, true)
internal val userRepoRemoteDetail = UserRepoDetailRemote(1, "Repo 1", "octocat/Repo 1", "Description 1", "Kotlin", false, "", 2, 1, 0)
internal val userRepoDetailPresentation = UserRepoDetailPresentation(1, "Repo 1", "octocat", "Description 1", "Kotlin", false, "", 2, 1, 0, true)

internal val fakeReposDTO = listOf(
    userRepoDTO1, userRepoDTO2, userRepoDTO3, userRepoDTO4
)
internal val fakeReposDBEntity = listOf(
    userRepoDBEntity1, userRepoDBEntity2, userRepoDBEntity3, userRepoDBEntity4
)
internal val fakeReposRemote = listOf(
    userRepoRemote1, userRepoRemote2, userRepoRemote3, userRepoRemote4
)
internal val fakeReposRemoteWithNullId = listOf(
    userRepoRemote1, userRepoRemote2, userRepoRemote3, userRepoRemote4, userRepoRemote5
)
internal val fakeReposPresentation = listOf(
    userRepoPresentation1, userRepoPresentation2, userRepoPresentation3, userRepoPresentation4
)