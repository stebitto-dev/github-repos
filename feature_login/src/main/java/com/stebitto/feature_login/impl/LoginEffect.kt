package com.stebitto.feature_login.impl

import com.stebitto.common.api.Effect

internal sealed class LoginEffect : Effect {
    data object LoginSuccess : LoginEffect()
}