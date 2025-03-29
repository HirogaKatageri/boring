package dev.hirogakatageri.boring

import android.app.Application
import dev.hirogakatageri.boring.core.databaseModule
import dev.hirogakatageri.boring.core.networkModule
import dev.hirogakatageri.boring.core.repositoryModule
import dev.hirogakatageri.boring.core.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Application class for initializing Koin dependency injection.
 */
class BoringApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            // Use Android logger for debug builds
            androidLogger(Level.NONE)
            // Provide Android context
            androidContext(this@BoringApplication)
            // Load Koin modules
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}