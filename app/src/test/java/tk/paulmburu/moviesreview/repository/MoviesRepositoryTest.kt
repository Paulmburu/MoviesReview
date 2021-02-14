package tk.paulmburu.moviesreview.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.utils.ResultState
import tk.paulmburu.moviesreview.utils.Success

class MoviesRepositoryTest : IMoviesRepository {

    var popularMoviesData: LinkedHashMap<String, Movie> = LinkedHashMap()
    private val observablePopularMovies = MutableLiveData<ResultState<List<Movie>>>()

    var upcomingMoviesData: LinkedHashMap<String, Movie> = LinkedHashMap()
    private val observableUpcomingMovies = MutableLiveData<ResultState<List<Movie>>>()

    override suspend fun getAvailablePopularMovies(): ResultState<List<Movie>> {
        return if(popularMoviesData.isEmpty()){
            Success(emptyList())
        }else {
            Success(popularMoviesData.values.toList())
        }
    }

    override suspend fun getAvailableUpcomingMovies(): ResultState<List<Movie>> {
        return if(upcomingMoviesData.isEmpty()){
            Success(emptyList())
        }else {
            Success(upcomingMoviesData.values.toList())
        }
    }

    fun observePopularMovies() : LiveData <ResultState<List<Movie>>>{
        runBlocking { refreshPopularMovies() }
        return observablePopularMovies
    }

    suspend fun refreshPopularMovies() {
        observablePopularMovies.value = getAvailablePopularMovies()
    }

    fun observeUpcomingMovies() : LiveData <ResultState<List<Movie>>>{
        runBlocking { refreshUpcomingMovies() }
        return observableUpcomingMovies
    }

    suspend fun refreshUpcomingMovies() {
        observableUpcomingMovies.value = getAvailablePopularMovies()
    }

    fun addPopularMovies(vararg movies: Movie) {
        for (movie in movies) {
            popularMoviesData[movie.title] = movie
        }
        runBlocking { refreshPopularMovies() }
    }

    fun addUpcomingMovies(vararg movies: Movie) {
        for (movie in movies) {
            upcomingMoviesData[movie.title] = movie
        }
        runBlocking { refreshUpcomingMovies() }
    }
}