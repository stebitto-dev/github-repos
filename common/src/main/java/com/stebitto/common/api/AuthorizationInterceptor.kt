package com.stebitto.common.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val userRepository: UserRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        userRepository.getGithubToken().fold(
            onSuccess = { token ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                return chain.proceed(newRequest)
            },
            onFailure = { exception ->
                throw exception
            }
        )
    }
}