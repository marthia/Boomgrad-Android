package me.marthia.app.boomgrad.presentation

import android.app.Application
import me.marthia.app.boomgrad.BuildConfig
import me.marthia.app.boomgrad.di.appModule
import me.marthia.app.boomgrad.di.cartModule
import me.marthia.app.boomgrad.di.networkModule
import me.marthia.app.boomgrad.di.repositoryModule
import me.marthia.app.boomgrad.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule, networkModule, repositoryModule, useCaseModule, cartModule)
        }

        // init
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}