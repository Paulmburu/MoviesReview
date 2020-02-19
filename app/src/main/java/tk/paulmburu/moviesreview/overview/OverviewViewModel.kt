package tk.paulmburu.moviesreview.overview

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tk.paulmburu.moviesreview.database.getDatabase
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.network.MovieResult
import tk.paulmburu.moviesreview.network.MoviesApi
import tk.paulmburu.moviesreview.repository.MoviesRepository

//MoviesApiStatus enum with the LOADING, ERROR, and DONE states
enum class MoviesApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel(application: Application) : ViewModel(){

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<MoviesApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<MoviesApiStatus>
            get() = _status

//    LiveData MoviesProperty property with an internal Mutable and an external LiveData
    private val _movieResults = MutableLiveData<List<MovieResult>>()

    val movieResults: LiveData<List<MovieResult>>
        get() = _movieResults

//    Add a _navigateToSelectedProperty MutableLiveData externalized as LiveData
    private val _navigateToSelectedMovie = MutableLiveData<Movie>()

    val navigateToSelectedMovie: LiveData<Movie>
        get() = _navigateToSelectedMovie

//    Create a coroutine Job and a CoroutineScope using the Main Dispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private val database = getDatabase(application)
    private val moviesRepository = MoviesRepository(database)

    /**
     * Call getPopularMoviesProperties() on init so we can display status immediately.
     */
    init {
        coroutineScope.launch {
            moviesRepository.refreshMovies()
        }
    }

    val playlist = moviesRepository.movies

    /**
     * Sets the value of the status LiveData to the Popular Movies API status.
     */
//    private fun getPopularMoviesProperties(){
//
//        coroutineScope.launch {
//            var getPropertiesDeferred = MoviesApi.retrofitService.getProperties()
//
//            try {
//                _status.value = MoviesApiStatus.LOADING
//                var result = getPropertiesDeferred.await()
//                _status.value = MoviesApiStatus.DONE
//                _movieResults.value = result.results
//            } catch (e: Exception) {
//                _status.value = MoviesApiStatus.ERROR
//                _movieResults.value = arrayListOf()
//            }
//        }
//
//    }

    // Add displayMovieDetails and displayMovieDetailsComplete methods
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

//    fun onSwipe(){
//        getPopularMoviesProperties()
//    }

    /**
     * Factory for constructing OverviewViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OverviewViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}