package com.stebitto.common.api

import com.stebitto.common.api.models.LoginDTO

interface GithubRepository {
    suspend fun login(username: String, password: String): Result<LoginDTO>
}