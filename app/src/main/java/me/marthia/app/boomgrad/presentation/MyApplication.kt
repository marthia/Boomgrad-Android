package me.marthia.app.boomgrad.presentation

import android.app.Application
import me.marthia.app.boomgrad.BuildConfig
import me.marthia.app.boomgrad.di.appModule
import me.marthia.app.boomgrad.di.networkModule
import me.marthia.app.boomgrad.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            workManagerFactory() // Important! Enables WorkManager factory
            modules(appModule, networkModule, repositoryModule)
        }

        // init
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}