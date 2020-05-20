package tk.paulmburu.moviesreview.ui.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.paulmburu.moviesreview.domain.Movie
import javax.inject.Inject

class DetailViewModel  @Inject constructor() : ViewModel(){

    private val _selectedMovie = MutableLiveData<Movie>()

    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie


    fun setSelectedMovie(movie: Movie){
        _selectedMovie.value = movie
    }
}