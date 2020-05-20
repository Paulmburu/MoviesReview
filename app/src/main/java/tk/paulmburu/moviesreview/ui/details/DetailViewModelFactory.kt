package tk.paulmburu.moviesreview.ui.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tk.paulmburu.moviesreview.domain.Movie

/**
 * Simple ViewModel factory that provides the MoviesProperty and context to the ViewModel.
 */
//class DetailViewModelFactory(
//    private val movie: Movie,
//    private val application: Application
//) : ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
//            return DetailViewModel( movie, application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}