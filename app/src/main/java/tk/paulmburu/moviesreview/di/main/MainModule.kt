package tk.paulmburu.moviesreview.di.main

import dagger.Module
import dagger.Provides
import tk.paulmburu.moviesreview.interactors.GetAvailablePopularMoviesUseCase
import tk.paulmburu.moviesreview.interactors.GetAvailableUpcomingMoviesUseCase
import tk.paulmburu.moviesreview.repository.MoviesRepository

@Module
class MainModule {
    @Provides
    fun providePopularMoviesUseCase(
        repository: MoviesRepository
    ) : GetAvailablePopularMoviesUseCase = GetAvailablePopularMoviesUseCase(repository)

    @Provides
    fun provideUpcomingMoviesUseCase(
        repository: MoviesRepository
    ) : GetAvailableUpcomingMoviesUseCase = GetAvailableUpcomingMoviesUseCase(repository)
}