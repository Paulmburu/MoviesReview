package tk.paulmburu.moviesreview.models

import com.squareup.moshi.Json
import tk.paulmburu.moviesreview.models.MovieResult

data class MoviesProperties(
    @Json(name = "results")val results: List<MovieResult> = ArrayList<MovieResult>()
)