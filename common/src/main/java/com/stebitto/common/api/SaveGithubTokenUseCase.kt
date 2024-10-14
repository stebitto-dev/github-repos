package com.stebitto.common.api

interface SaveGithubTokenUseCase {
    suspend operator fun invoke(token: String)
}