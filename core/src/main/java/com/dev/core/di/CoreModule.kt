package com.dev.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.dev.core.BuildConfig
import com.dev.core.data.dataStore.DataStore
import com.dev.core.data.firebase.FirebaseDataSource
import com.dev.core.data.local.BaseDb
import com.dev.core.data.local.LocalDataSource
import com.dev.core.data.remote.service.ApiService
import com.dev.core.data.remote.service.ApiServiceImpl
import com.dev.core.data.remote.source.RemoteDataSource
import com.dev.core.data.repository.IRepository
import com.dev.core.data.repository.Repository
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {

        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.SQL_PASSWORD.toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            BaseDb::class.java,
            "Base.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()

    }
}

val networkModule = module {
    single {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(HttpTimeout) { // Timeout
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
        }
    }
    single<ApiService> { ApiServiceImpl(get()) }
}

val repositoryModule = module {

    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { DataStore(androidContext()) }
    single { FirebaseDataSource() }

    single<IRepository> { Repository(get(), get(), get(), get()) }

}