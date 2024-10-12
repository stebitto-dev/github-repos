package com.stebitto.feature_login.api.data

import com.stebitto.common.api.models.LoginDTO

interface GithubLoginUseCase {
    suspend operator fun invoke(username: String, password: String): Result<LoginDTO>
}