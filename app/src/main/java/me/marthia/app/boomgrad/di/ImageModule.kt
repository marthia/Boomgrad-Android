package me.marthia.app.boomgrad.di

import coil.ImageLoaderFactory
import me.marthia.app.boomgrad.data.remote.image.CoilFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val imageModule = module {
    // Provide the Base Path (perhaps from a config file or BuildKonfig)
    single(named("base_url")) { ApiConfig.BASE_URL }

    // Provide the ImageLoaderFactory
    single<ImageLoaderFactory> {
        CoilFactory(androidContext(), get(named("base_url")))
    }
    
    // Provide the actual ImageLoader
    single { get<ImageLoaderFactory>().newImageLoader() }
}