package com.stebitto.feature_user_repos.impl.data.usecases

interface SignOutUseCase {
    suspend operator fun invoke(): Result<Unit>
}