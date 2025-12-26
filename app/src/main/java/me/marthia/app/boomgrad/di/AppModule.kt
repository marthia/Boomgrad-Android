package me.marthia.app.boomgrad.di


import me.marthia.app.boomgrad.presentation.attraction.AttractionsViewModel
import me.marthia.app.boomgrad.presentation.attraction.detail.AttractionDetailViewModel
import me.marthia.app.boomgrad.presentation.components.SnackbarManager
import me.marthia.app.boomgrad.presentation.favorites.FavoritesViewModel
import me.marthia.app.boomgrad.presentation.login.LoginViewModel
import me.marthia.app.boomgrad.presentation.login.otp.OtpViewModel
import me.marthia.app.boomgrad.presentation.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for providing ViewModels.
 */
val appModule = module {
    viewModel { AttractionsViewModel(get()) }
    viewModel { AttractionDetailViewModel(get(), get()) }
    viewModel { FavoritesViewModel(repository = get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), SnackbarManager) }
    viewModel { OtpViewModel(repository = get(), context = androidContext()) }
}