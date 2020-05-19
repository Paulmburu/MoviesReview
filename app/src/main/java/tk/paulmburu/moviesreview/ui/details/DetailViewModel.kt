package tk.paulmburu.moviesreview.ui.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.paulmburu.moviesreview.domain.Movie

class DetailViewModel(movie: Movie, app : Application) : ViewModel(){

    private val _selectedMovie = MutableLiveData<Movie>()

    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }


}