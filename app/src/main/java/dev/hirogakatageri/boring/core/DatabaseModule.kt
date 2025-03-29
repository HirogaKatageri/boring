package dev.hirogakatageri.boring.core

import androidx.room.Room
import dev.hirogakatageri.boring.data.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module for database-related dependencies.
 */
val databaseModule = module {
    // Provide AppDatabase
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    // Provide UserDao
    single {
        get<AppDatabase>().userDao()
    }
}