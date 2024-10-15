package com.stebitto.common.api

interface UserRepository {
    fun getGithubToken(): Result<String>
    fun saveGithubToken(token: String)
    fun saveUserName(name: String)
    fun getUserName(): Result<String>
    fun clearAll()
}