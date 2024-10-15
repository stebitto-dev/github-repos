package com.stebitto.feature_login.impl

import com.stebitto.common.api.Intent

internal sealed class LoginIntent : Intent {
    data object LoginButtonClicked : LoginIntent()
    data class LoginSuccess(val accessToken: String) : LoginIntent()
    data class LoginFailed(val errorMessage: String?) : LoginIntent()
}
