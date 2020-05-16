package tk.paulmburu.moviesreview.base

import android.os.Build
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.errorview.*
import kotlinx.android.synthetic.main.movies_loading_animation.*
import tk.paulmburu.moviesreview.utils.*

abstract class BaseFragment<T>() : Fragment() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onNetworkChange(block: (Boolean) -> Unit) {
        NetworkUtils.getNetworkStatus(context!!)
            .observe(this.viewLifecycleOwner, Observer { isConnected ->
                block(isConnected)
            })
    }

    fun handleState(result: ResultState<T>) {
        when (result) {
            is Error -> showOnError(ExceptionHandler(context!!).parse(result.e))
            is Success -> showOnSuccess(result)
            is Loading -> showLoading(true)
        }
    }

    open fun showLoading(isLoading: Boolean) {
        movies_loading_anim.isVisible(isLoading)
        error_text_view.isVisible(false)
    }

    open fun showOnSuccess(result: Success<T>) {
        showLoading(false)
    }

    fun showOnError(message: String) {
        showLoading(false)
        error_text_view.text = message
        error_text_view.isVisible(true)
    }
}