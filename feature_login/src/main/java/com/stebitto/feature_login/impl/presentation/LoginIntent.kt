package com.stebitto.feature_login.impl.presentation

import com.stebitto.common.api.Intent

internal sealed class LoginIntent : Intent {
    data object LoginButtonClicked : LoginIntent()
    data object LoginSuccess : LoginIntent()
    data class LoginFailed(val errorMessage: String?) : LoginIntent()
}
