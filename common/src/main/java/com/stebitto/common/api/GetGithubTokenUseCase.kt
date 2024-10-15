package com.stebitto.common.api

interface GetGithubTokenUseCase {
    operator fun invoke(): Result<String>
}