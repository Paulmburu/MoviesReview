package tk.paulmburu.moviesreview.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.paulmburu.moviesreview.models.MovieResult

class DetailViewModel(movieResult: MovieResult, app : Application) : ViewModel(){
//    Add selected MovieLiveData, and initialize during init

    private val _selectedMovie = MutableLiveData<MovieResult>()

    val selectedMovie: LiveData<MovieResult>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movieResult
    }


}