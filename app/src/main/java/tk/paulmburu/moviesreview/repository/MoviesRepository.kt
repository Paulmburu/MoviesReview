package tk.paulmburu.moviesreview.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tk.paulmburu.moviesreview.database.MoviesDatabase
import tk.paulmburu.moviesreview.database.asDomainModel
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.network.*
import tk.paulmburu.moviesreview.utils.ResultState
import tk.paulmburu.moviesreview.utils.Success
import tk.paulmburu.moviesreview.utils.executeNonBlocking

class MoviesRepository (private val database: MoviesDatabase){


    suspend fun getAvailablePopularMovies(): ResultState<List<Movie>>{
        return executeNonBlocking {
            if(database.movieDao.getPopularMovies().isNullOrEmpty()){
                val response = MoviesApi.retrofitService.getPopularMovies()
                if(response.results.size == 0 )
                    Success(emptyList<Movie>())
                else{
                    val result = MoviesApi.retrofitService.getPopularMovies()
                    database.movieDao.insertAllPopularMovies(*result.asDatabasePopularMoviesModel())
                    Success(database.movieDao.getPopularMovies().asDomainModel())
                }
            } else Success(database.movieDao.getPopularMovies().asDomainModel())
        }
    }

    suspend fun getAvailableUpcomingMovies(): ResultState<List<Movie>>{
        return executeNonBlocking {
            if(database.movieDao.getUpcomingMovies().isNullOrEmpty()){
                val response = MoviesApi.retrofitService.getUpcomingMovies()
                if(response.results.size == 0 )
                    Success(emptyList<Movie>())
                else{
                    val result = MoviesApi.retrofitService.getUpcomingMovies()
                    database.movieDao.insertAllUpcomingMovies(*result.asDatabaseUpcomingMoviesModel())
                    Success(database.movieDao.getUpcomingMovies().asDomainModel())
                }
            } else Success(database.movieDao.getUpcomingMovies().asDomainModel())
        }
    }

}


