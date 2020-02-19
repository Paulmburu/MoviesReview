package tk.paulmburu.moviesreview.network

import com.squareup.moshi.Json
import tk.paulmburu.moviesreview.network.MovieResult

data class MoviesProperties(
    @Json(name = "results")val results: List<NetworkMovieContainer>
)