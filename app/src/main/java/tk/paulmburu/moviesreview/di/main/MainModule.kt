package tk.paulmburu.moviesreview.di.main

import dagger.Module
import dagger.Provides
import tk.paulmburu.moviesreview.interactors.GetAvailablePopularMoviesUseCase
import tk.paulmburu.moviesreview.interactors.GetAvailableUpcomingMoviesUseCase
import tk.paulmburu.moviesreview.interactors.IGetAvailablePopularMoviesUseCase
import tk.paulmburu.moviesreview.interactors.IGetAvailableUpcomingMoviesUseCase
import tk.paulmburu.moviesreview.repository.MoviesRepository

@Module
class MainModule {
    @Provides
    fun providePopularMoviesUseCase(
        repository: MoviesRepository
    ) : IGetAvailablePopularMoviesUseCase = GetAvailablePopularMoviesUseCase(repository)

    @Provides
    fun provideUpcomingMoviesUseCase(
        repository: MoviesRepository
    ) : IGetAvailableUpcomingMoviesUseCase = GetAvailableUpcomingMoviesUseCase(repository)
}