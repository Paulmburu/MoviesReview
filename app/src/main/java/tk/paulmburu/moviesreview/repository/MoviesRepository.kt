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

class MoviesRepository (private val database: MoviesDatabase){

    /**
     * A list of movies that can be shown on the screen.
     */
    val movies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.getMovies()) {
            it.asDomainModel()
        }

    /**
     * Refresh the videos stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the videos for use, observe [videos]
     */
    suspend fun refreshMovies(){
        withContext(Dispatchers.IO){
            val result = MoviesApi.retrofitService.getPopularMovies().await()
            database.movieDao.deleteAllMovies()
            database.movieDao.insertAll(*result.asDatabaseModel())
        }

    }

    suspend fun getUpcomingMovies(){
        withContext(Dispatchers.IO){
            val result = MoviesApi.retrofitService.getUpcomingMovies().await()
            database.movieDao.deleteAllMovies()
            database.movieDao.insertAll(*result.asDatabaseModel())
        }
    }


}