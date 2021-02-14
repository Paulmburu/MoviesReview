package tk.paulmburu.moviesreview.interactors


import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.repository.MoviesRepositoryTest
import tk.paulmburu.moviesreview.utils.ResultState

class GetAvailablePopularMoviesUseCaseTest(val moviesRepository: MoviesRepositoryTest) : IGetAvailablePopularMoviesUseCase {

    override suspend fun invoke(): ResultState<List<Movie>> {
        return moviesRepository.getAvailablePopularMovies()
    }

}