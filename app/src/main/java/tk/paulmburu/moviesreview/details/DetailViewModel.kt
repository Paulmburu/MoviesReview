package tk.paulmburu.moviesreview.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.network.MovieResult

class DetailViewModel(movie: Movie, app : Application) : ViewModel(){
//    Add selected MovieLiveData, and initialize during init

    private val _selectedMovie = MutableLiveData<Movie>()

    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }


}