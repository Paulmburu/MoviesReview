package tk.paulmburu.moviesreview.repository


import tk.paulmburu.moviesreview.database.MoviesDatabase
import tk.paulmburu.moviesreview.database.asDomainMovieModel
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
                    Success(database.movieDao.getPopularMovies().asDomainMovieModel())
                }
            } else Success(database.movieDao.getPopularMovies().asDomainMovieModel())
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
                    Success(database.movieDao.getUpcomingMovies().asDomainMovieModel())
                }
            } else Success(database.movieDao.getUpcomingMovies().asDomainMovieModel())
        }
    }

}


