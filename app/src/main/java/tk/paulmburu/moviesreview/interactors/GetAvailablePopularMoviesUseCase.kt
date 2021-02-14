package tk.paulmburu.moviesreview.interactors

import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.repository.MoviesRepository
import tk.paulmburu.moviesreview.utils.ResultState
import javax.inject.Inject

class GetAvailablePopularMoviesUseCase
@Inject constructor(val moviesRepository: MoviesRepository) : IGetAvailablePopularMoviesUseCase {

    override suspend fun invoke(): ResultState<List<Movie>> {
        return moviesRepository.getAvailablePopularMovies()
    }
}

interface IGetAvailablePopularMoviesUseCase {
    suspend fun invoke(): ResultState<List<Movie>>
}