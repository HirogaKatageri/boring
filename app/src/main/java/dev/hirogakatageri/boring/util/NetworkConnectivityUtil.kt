package dev.hirogakatageri.boring.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Utility class for checking network connectivity.
 */
object NetworkConnectivityUtil : KoinComponent {
    private val context: Context
        get() = getKoin().get()

    /**
     * Check if the device is connected to the internet.
     * 
     * @return True if the device is connected to the internet, false otherwise
     */
    fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
