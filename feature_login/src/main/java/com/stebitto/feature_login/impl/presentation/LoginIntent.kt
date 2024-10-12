package com.stebitto.feature_login.impl.presentation

import com.stebitto.common.api.Intent

internal sealed class LoginIntent : Intent {
    data class Login(val username: String, val password: String) : LoginIntent()
}
