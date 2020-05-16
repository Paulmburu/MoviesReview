package tk.paulmburu.moviesreview.interactors

import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.repository.MoviesRepository
import tk.paulmburu.moviesreview.utils.ResultState

class GetAvailableMoviesUseCase(val moviesRepository: MoviesRepository) {

    suspend fun invoke(): ResultState<List<Movie>>{
        return moviesRepository.getAvailableMovies()
    }
}