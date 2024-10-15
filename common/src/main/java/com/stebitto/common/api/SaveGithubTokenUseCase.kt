package com.stebitto.common.api

interface SaveGithubTokenUseCase {
    operator fun invoke(token: String)
}