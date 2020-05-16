package tk.paulmburu.moviesreview.ui.overview

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tk.paulmburu.moviesreview.database.getDatabase
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.interactors.GetAvailableMoviesUseCase
import tk.paulmburu.moviesreview.network.MovieResult
import tk.paulmburu.moviesreview.repository.MoviesRepository
import tk.paulmburu.moviesreview.utils.Loading
import tk.paulmburu.moviesreview.utils.ResultState

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

    init {
        getAvailableMovies()
    }

    private val _movies = MutableLiveData<ResultState<List<Movie>>>()

    val movies: LiveData<ResultState<List<Movie>>>
        get() = _movies

    fun getAvailableMovies(){
//        _movies.value = Loading<List<Movie>>()
        coroutineScope.launch(Dispatchers.IO) {
            _movies.postValue(GetAvailableMoviesUseCase(moviesRepository).invoke())
        }
    }


    // Add displayMovieDetails and displayMovieDetailsComplete methods
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    fun onSwipe() {
        coroutineScope.launch {
            moviesRepository.refreshMovies()
        }
    }

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