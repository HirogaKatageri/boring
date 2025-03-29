package dev.hirogakatageri.boring.core

import dev.hirogakatageri.boring.user.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module for repository-related dependencies.
 */
val repositoryModule = module {
    // Provide UserRepository
    single {
        UserRepository(
            userApiService = get(),
            userDao = get()
        )
    }
}
