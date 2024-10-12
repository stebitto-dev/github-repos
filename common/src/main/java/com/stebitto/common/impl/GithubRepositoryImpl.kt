package com.stebitto.common.impl

import com.stebitto.common.api.GithubRepository
import com.stebitto.common.api.models.LoginDTO

internal class GithubRepositoryImpl : GithubRepository {
    override suspend fun login(username: String, password: String): Result<LoginDTO> {
        TODO("Not yet implemented")
    }
}