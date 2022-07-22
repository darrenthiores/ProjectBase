package com.dev.base

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.dev.base.di.useCaseModule
import com.dev.base.di.viewModelModule
import com.dev.core.di.databaseModule
import com.dev.core.di.networkModule
import com.dev.core.di.repositoryModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

open class MyApplication : Application() {

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {

            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )

        }

    }
}