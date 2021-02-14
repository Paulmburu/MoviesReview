package tk.paulmburu.moviesreview.ui.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tk.paulmburu.moviesreview.getOrAwaitValue
import tk.paulmburu.moviesreview.interactors.*
import tk.paulmburu.moviesreview.repository.MoviesRepositoryTest
import tk.paulmburu.moviesreview.utils.Data.movie1
import tk.paulmburu.moviesreview.utils.Data.movie2
import tk.paulmburu.moviesreview.utils.Data.movie3
import tk.paulmburu.moviesreview.utils.Data.movie4
import tk.paulmburu.moviesreview.utils.Data.movie5
import tk.paulmburu.moviesreview.utils.Data.movie6
import tk.paulmburu.moviesreview.utils.Success

class OverviewViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var overviewViewModel: OverviewViewModel
    private lateinit var popularMoviesUseCase: IGetAvailablePopularMoviesUseCase
    private lateinit var upcomingMoviesUseCase: IGetAvailableUpcomingMoviesUseCase
    private lateinit var moviesRepositoryTest: MoviesRepositoryTest

    @Before
    fun setupViewModel() {
        moviesRepositoryTest = MoviesRepositoryTest()
        popularMoviesUseCase = GetAvailablePopularMoviesUseCaseTest(moviesRepositoryTest)
        upcomingMoviesUseCase = GetAvailableUpcomingMoviesUseCaseTest(moviesRepositoryTest)
        overviewViewModel = OverviewViewModel(popularMoviesUseCase, upcomingMoviesUseCase)
    }

    @Test
    fun addPopularMovies_MoviesLiveDataNotEmpty() {

        moviesRepositoryTest.addPopularMovies(movie1, movie2,movie3)

        overviewViewModel.getAvailablePopularMovies()

        val movies = overviewViewModel.movies.getOrAwaitValue { }

        movies.run {
            when (this) {
                is Success -> {
                    assertThat(this.data, not(emptyList()))
                }
            }
        }

    }

    @Test
    fun addUpcomingMovies_MoviesLiveDataNotEmpty() {

        moviesRepositoryTest.addUpcomingMovies(movie4, movie5,movie6)

        overviewViewModel.getAvailableUpcomingMovies()

        val movies = overviewViewModel.movies.getOrAwaitValue { }

        movies.run {
            when (this) {
                is Success -> {
                    assertThat(this.data, not(emptyList()))
                }
            }
        }

    }

}