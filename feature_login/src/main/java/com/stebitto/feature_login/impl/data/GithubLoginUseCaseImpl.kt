package com.stebitto.feature_login.impl.data

import com.stebitto.common.api.models.LoginDTO
import com.stebitto.feature_login.api.data.GithubLoginUseCase

internal class GithubLoginUseCaseImpl : GithubLoginUseCase {
    override suspend fun invoke(username: String, password: String): Result<LoginDTO> {
        TODO("Not yet implemented")
    }
}