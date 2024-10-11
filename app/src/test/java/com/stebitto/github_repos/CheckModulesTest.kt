package com.stebitto.github_repos

import com.stebitto.feature_login.api.featureLoginModule
import com.stebitto.github_repos.di.appModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class CheckModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAllModules() {
        appModule.verify()
        featureLoginModule.verify()
    }
}