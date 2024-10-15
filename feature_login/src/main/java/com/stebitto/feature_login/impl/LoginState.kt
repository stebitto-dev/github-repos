package com.stebitto.feature_login.impl

import com.stebitto.common.api.State

internal data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
) : State