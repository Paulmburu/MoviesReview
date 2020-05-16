package tk.paulmburu.moviesreview.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tk.paulmburu.moviesreview.database.MoviesDatabase
import tk.paulmburu.moviesreview.database.asDomainModel
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.network.MoviesApi
import tk.paulmburu.moviesreview.network.NetworkMovie
import tk.paulmburu.moviesreview.network.asDatabaseModel
import tk.paulmburu.moviesreview.utils.ResultState
import tk.paulmburu.moviesreview.utils.Success
import tk.paulmburu.moviesreview.utils.executeNonBlocking

class MoviesRepository (private val database: MoviesDatabase){


    suspend fun getAvailableMovies(): ResultState<List<Movie>>{
        return executeNonBlocking {
            if(database.movieDao.getMovies().isNullOrEmpty()){
                val response = MoviesApi.retrofitService.getPopularMovies()
                if(response.results.size == 0 )
                    Success(emptyList<Movie>())
                else{
                    val result = MoviesApi.retrofitService.getPopularMovies()
                    database.movieDao.deleteAllMovies()
                    database.movieDao.insertAll(*result.asDatabaseModel())
                    Success(database.movieDao.getMovies().asDomainModel())
                }
            } else Success(database.movieDao.getMovies().asDomainModel())
        }
    }

    suspend fun refreshMovies(){
        withContext(Dispatchers.IO){
            val result = MoviesApi.retrofitService.getPopularMovies()
            database.movieDao.deleteAllMovies()
            database.movieDao.insertAll(*result.asDatabaseModel())
        }

    }

    suspend fun getUpcomingMovies(){
        withContext(Dispatchers.IO){
            val result = MoviesApi.retrofitService.getUpcomingMovies()
            database.movieDao.deleteAllMovies()
            database.movieDao.insertAll(*result.asDatabaseModel())
        }
    }

}


