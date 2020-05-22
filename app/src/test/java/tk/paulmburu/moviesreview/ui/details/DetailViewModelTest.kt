package tk.paulmburu.moviesreview.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import tk.paulmburu.moviesreview.domain.Movie
import org.junit.Rule
import tk.paulmburu.moviesreview.getOrAwaitValue


class DetailViewModelTest{

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun setSelectedMovie_NewMovie_returnsMovie(){
        // Given a fresh TasksViewModel
        val viewModel = DetailViewModel()

        // When adding a new Movie
        viewModel.setSelectedMovie((Movie("TestMovie","","","","","")))

        // Then the new movie event is triggered
        val value = viewModel.selectedMovie.getOrAwaitValue()
        assertThat(value,`is`(Movie("TestMovie","","","","","")))
    }
}