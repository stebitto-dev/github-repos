package com.stebitto.feature_user_repos.impl.data.usecases

import com.google.firebase.auth.FirebaseAuth
import com.stebitto.common.api.UserRepository
import com.stebitto.feature_user_repos.api.GithubRepository

class SignOutUseCaseImpl(
    private val userRepository: UserRepository,
    private val githubRepository: GithubRepository
) : SignOutUseCase {
    override suspend fun invoke(): Result<Unit> = runCatching {
        userRepository.clearGithubToken()
        githubRepository.clearUserRepos()
        FirebaseAuth.getInstance().signOut()
        return Result.success(Unit)
    }
}