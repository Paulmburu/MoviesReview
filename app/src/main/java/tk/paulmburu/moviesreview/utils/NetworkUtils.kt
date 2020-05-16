package tk.paulmburu.moviesreview.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.net.ConnectException
import java.net.UnknownHostException

object NetworkUtils {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getNetworkStatus(
        context: Context
    ): LiveData<Boolean> {
        val isAvailableLiveData = MutableLiveData<Boolean>()
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nr = NetworkRequest.Builder()

        cm.registerNetworkCallback(nr.build(), @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isAvailableLiveData.postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isAvailableLiveData.postValue(false)

            }
        })
        return isAvailableLiveData
    }

}

suspend fun <T> executeNonBlocking(
    execute: suspend () -> ResultState<T>
): ResultState<T> {
    return try {
        execute()
    } catch (e: UnknownHostException) {
        Error<T>(e)
    } catch (e: ConnectException) {
        Error<T>(e)
    }

}