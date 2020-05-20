package tk.paulmburu.moviesreview.repository


import tk.paulmburu.moviesreview.database.MoviesDatabase
import tk.paulmburu.moviesreview.database.asDomainMovieModel
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.network.MoviesApiService
import tk.paulmburu.moviesreview.network.asDatabasePopularMoviesModel
import tk.paulmburu.moviesreview.network.asDatabaseUpcomingMoviesModel
import tk.paulmburu.moviesreview.utils.ResultState
import tk.paulmburu.moviesreview.utils.Success
import tk.paulmburu.moviesreview.utils.executeNonBlocking
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesApiService: MoviesApiService ,private val database: MoviesDatabase){


    suspend fun getAvailablePopularMovies(): ResultState<List<Movie>>{
        return executeNonBlocking {
            if(database.movieDao.getPopularMovies().isNullOrEmpty()){
                val response = moviesApiService.getPopularMovies()
                if(response.results.size == 0 )
                    Success(emptyList<Movie>())
                else{
                    val result = moviesApiService.getPopularMovies()
                    database.movieDao.insertAllPopularMovies(*result.asDatabasePopularMoviesModel())
                    Success(database.movieDao.getPopularMovies().asDomainMovieModel())
                }
            } else Success(database.movieDao.getPopularMovies().asDomainMovieModel())
        }
    }

    suspend fun getAvailableUpcomingMovies(): ResultState<List<Movie>>{
        return executeNonBlocking {
            if(database.movieDao.getUpcomingMovies().isNullOrEmpty()){
                val response = moviesApiService.getUpcomingMovies()
                if(response.results.size == 0 )
                    Success(emptyList<Movie>())
                else{
                    val result = moviesApiService.getUpcomingMovies()
                    database.movieDao.insertAllUpcomingMovies(*result.asDatabaseUpcomingMoviesModel())
                    Success(database.movieDao.getUpcomingMovies().asDomainMovieModel())
                }
            } else Success(database.movieDao.getUpcomingMovies().asDomainMovieModel())
        }
    }

}


