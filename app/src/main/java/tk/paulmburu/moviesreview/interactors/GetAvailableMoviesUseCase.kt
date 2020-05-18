package tk.paulmburu.moviesreview.interactors

import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.repository.MoviesRepository
import tk.paulmburu.moviesreview.utils.ResultState

class GetAvailablePopularMoviesUseCase(val moviesRepository: MoviesRepository) {

    suspend fun invoke(): ResultState<List<Movie>>{
        return moviesRepository.getAvailablePopularMovies()
    }
}

class GetAvailableUpcomingMoviesUseCase(val moviesRepository: MoviesRepository) {

    suspend fun invoke(): ResultState<List<Movie>>{
        return moviesRepository.getAvailableUpcomingMovies()
    }
}