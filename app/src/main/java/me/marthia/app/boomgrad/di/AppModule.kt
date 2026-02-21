package me.marthia.app.boomgrad.di


import me.marthia.app.boomgrad.presentation.attraction.detail.AttractionDetailViewModel
import me.marthia.app.boomgrad.presentation.attraction.list.AttractionsViewModel
import me.marthia.app.boomgrad.presentation.components.SnackbarManager
import me.marthia.app.boomgrad.presentation.favorites.FavoritesViewModel
import me.marthia.app.boomgrad.presentation.guide.GuideInfoViewModel
import me.marthia.app.boomgrad.presentation.home.HomeViewModel
import me.marthia.app.boomgrad.presentation.login.LoginViewModel
import me.marthia.app.boomgrad.presentation.profile.ProfileViewModel
import me.marthia.app.boomgrad.presentation.search.SearchViewModel
import me.marthia.app.boomgrad.presentation.tour.detail.TourDetailViewModel
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
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get(), SnackbarManager) }
    viewModel { TourDetailViewModel(get(), get()) }
    viewModel { GuideInfoViewModel(get(), get()) }

    viewModel {
        HomeViewModel(
            getCity = get(),
            getCategories = get(),
            getForYouTours = get(),
            getTopAttractions = get(),
            getWeekRecommended = get(),
        )
    }
}