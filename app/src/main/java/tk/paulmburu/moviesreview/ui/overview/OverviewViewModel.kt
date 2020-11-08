package tk.paulmburu.moviesreview.ui.overview

import androidx.lifecycle.*
import kotlinx.coroutines.*
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.interactors.GetAvailablePopularMoviesUseCase
import tk.paulmburu.moviesreview.interactors.GetAvailableUpcomingMoviesUseCase
import tk.paulmburu.moviesreview.network.MovieResult
import tk.paulmburu.moviesreview.utils.ResultState
import javax.inject.Inject

//MoviesApiStatus enum with the LOADING, ERROR, and DONE states
enum class MoviesApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel
    @Inject constructor(private val getAvailablePopularMoviesUseCase: GetAvailablePopularMoviesUseCase,
     private val getAvailableUpcomingMoviesUseCase: GetAvailableUpcomingMoviesUseCase) : ViewModel(){
    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<MoviesApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<MoviesApiStatus>
            get() = _status

    private val _overFlowMenuState = MutableLiveData<OverflowMenuState>()
    val overFlowMenuState: LiveData<OverflowMenuState>
        get() = _overFlowMenuState

    fun setOverflowMenuState(state: OverflowMenuState){
        _overFlowMenuState.postValue(state)
    }

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

//    private val database = getDatabase(application)
//    private val moviesRepository = MoviesRepository(database)

    init {
        getAvailablePopularMovies()
    }

    private val _movies = MutableLiveData<ResultState<List<Movie>>>()

    val movies: LiveData<ResultState<List<Movie>>>
        get() = _movies

    fun getAvailablePopularMovies(){
//        _movies.value = Loading<List<Movie>>()
        coroutineScope.launch(Dispatchers.IO) {
            _movies.postValue(getAvailablePopularMoviesUseCase.invoke())
            _overFlowMenuState.postValue(PopularMoviesState())
        }
    }

    fun getAvailableUpcomingMovies(){
//        _movies.value = Loading<List<Movie>>()
        coroutineScope.launch(Dispatchers.IO) {
            _movies.postValue(getAvailableUpcomingMoviesUseCase.invoke())
            _overFlowMenuState.postValue(UpcomingMoviesState())
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

        when(overFlowMenuState.value){
            is PopularMoviesState ->{
                coroutineScope.launch(Dispatchers.IO) {
                    _movies.postValue(getAvailablePopularMoviesUseCase.invoke())
                }
            }
            is UpcomingMoviesState -> {
                coroutineScope.launch(Dispatchers.IO) {
                    _movies.postValue(getAvailableUpcomingMoviesUseCase.invoke())
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}

sealed class OverflowMenuState
data class PopularMoviesState(val title: String = "Popular Movies") : OverflowMenuState()
data class UpcomingMoviesState(val title: String = "Upcoming Movies") : OverflowMenuState()

