package com.stebitto.common.api

interface GetGithubTokenUseCase {
    suspend operator fun invoke(): Result<String>
}