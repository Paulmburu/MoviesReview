package tk.paulmburu.moviesreview.repository

import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.utils.ResultState

interface IMoviesRepository {
    suspend fun getAvailablePopularMovies(): ResultState<List<Movie>>

    suspend fun getAvailableUpcomingMovies(): ResultState<List<Movie>>
}