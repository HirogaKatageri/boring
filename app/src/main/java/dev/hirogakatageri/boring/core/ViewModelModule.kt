package dev.hirogakatageri.boring.core

import dev.hirogakatageri.boring.user.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for view model-related dependencies.
 */
val viewModelModule = module {
    // Provide UserViewModel
    viewModel {
        UserViewModel(repository = get())
    }
}