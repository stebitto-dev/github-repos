package com.stebitto.feature_login.api

import com.stebitto.feature_login.api.data.GithubLoginUseCase
import com.stebitto.feature_login.impl.data.GithubLoginUseCaseImpl
import com.stebitto.feature_login.impl.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureLoginModule = module {
    viewModel { LoginViewModel(get()) }
    factory<GithubLoginUseCase> { GithubLoginUseCaseImpl() }
}