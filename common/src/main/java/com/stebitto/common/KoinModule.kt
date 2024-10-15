package com.stebitto.common

import com.stebitto.common.api.UserRepository
import com.stebitto.common.impl.UserRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    single<UserRepository> { UserRepositoryImpl(androidContext()) }
}