package com.stebitto.common.api

interface UserRepository {
    fun getGithubToken(): Result<String>
    fun saveGithubToken(token: String)
    fun clearGithubToken()
}