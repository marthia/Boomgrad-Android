package me.marthia.app.boomgrad.presentation

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import me.marthia.app.boomgrad.BuildConfig
import me.marthia.app.boomgrad.di.appModule
import me.marthia.app.boomgrad.di.imageModule
import me.marthia.app.boomgrad.di.networkModule
import me.marthia.app.boomgrad.di.repositoryModule
import me.marthia.app.boomgrad.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.get
import timber.log.Timber

class MyApplication : Application() , ImageLoaderFactory {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule, networkModule, repositoryModule, useCaseModule, imageModule)
        }

        // init
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    override fun newImageLoader(): ImageLoader = get(ImageLoader::class.java)

}