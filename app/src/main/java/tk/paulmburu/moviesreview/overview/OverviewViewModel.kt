package tk.paulmburu.moviesreview.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tk.paulmburu.moviesreview.models.MovieResult
import tk.paulmburu.moviesreview.network.MoviesApi

//MarsApiStatus enum with the LOADING, ERROR, and DONE states
enum class MarsApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel(){

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<MarsApiStatus>
            get() = _status

//    LiveData MoviesProperty property with an internal Mutable and an external LiveData
    private val _movieResults = MutableLiveData<List<MovieResult>>()

    val movieResults: LiveData<List<MovieResult>>
        get() = _movieResults

//    Add a _navigateToSelectedProperty MutableLiveData externalized as LiveData
    private val _navigateToSelectedMovie = MutableLiveData<MovieResult>()

    val navigateToSelectedMovie: LiveData<MovieResult>
        get() = _navigateToSelectedMovie

//    Create a coroutine Job and a CoroutineScope using the Main Dispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    /**
     * Call getPopularMoviesProperties() on init so we can display status immediately.
     */
    init {
        getPopularMoviesProperties()
    }

    /**
     * Sets the value of the status LiveData to the Popular Movies API status.
     */
    private fun getPopularMoviesProperties(){

        coroutineScope.launch {
            var getPropertiesDeferred = MoviesApi.retrofitService.getProperties()

            try {
                _status.value = MarsApiStatus.LOADING
                var result = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE
                _movieResults.value = result.results
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _movieResults.value = arrayListOf()
            }
        }

    }

//    Add displayMovieDetails and displayMovieDetailsComplete methods
    fun displayMovieDetails(marsProperty: MovieResult) {
        _navigateToSelectedMovie.value = marsProperty
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    fun onSwipe(){
        getPopularMoviesProperties()
    }
}