package tk.paulmburu.moviesreview.interactors

import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.repository.MoviesRepositoryTest
import tk.paulmburu.moviesreview.utils.ResultState


class GetAvailableUpcomingMoviesUseCaseTest(val moviesRepositoryTest: MoviesRepositoryTest) : IGetAvailableUpcomingMoviesUseCase {
    override suspend fun invoke(): ResultState<List<Movie>> {
        return moviesRepositoryTest.getAvailableUpcomingMovies()
    }

}