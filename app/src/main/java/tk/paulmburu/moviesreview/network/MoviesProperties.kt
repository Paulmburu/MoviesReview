package tk.paulmburu.moviesreview.network

import com.squareup.moshi.Json

data class MoviesProperties(
    @Json(name = "results")val results: List<NetworkMovieContainer>
)