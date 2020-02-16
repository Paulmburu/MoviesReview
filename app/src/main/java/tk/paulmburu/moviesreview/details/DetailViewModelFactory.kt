package tk.paulmburu.moviesreview.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tk.paulmburu.moviesreview.models.MovieResult

/**
 * Simple ViewModel factory that provides the MarsProperty and context to the ViewModel.
 */
class DetailViewModelFactory(
    private val movieResult: MovieResult,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movieResult, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}