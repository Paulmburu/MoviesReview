package tk.paulmburu.moviesreview.utils

import android.content.Context
import tk.paulmburu.moviesreview.R
import java.net.ConnectException
import java.net.UnknownHostException

class ExceptionHandler(val context: Context) {

    fun parse(exception: Exception): String {
        return when (exception) {
            is ConnectException -> context.getString(R.string.no_internet_connection)
            is UnknownHostException -> context.getString(R.string.no_internet_connection)
            else -> exception.message!!
        }
    }
}