package com.stebitto.github_repos

import android.app.Application
import com.stebitto.common.api.commonModule
import com.stebitto.feature_login.api.featureLoginModule
import com.stebitto.github_repos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, featureLoginModule, commonModule)
        }
    }
}