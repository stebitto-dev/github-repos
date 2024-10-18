package com.stebitto.feature_login.api

import com.stebitto.feature_login.impl.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureLoginModule = module {
    viewModel { LoginViewModel(get()) }
}