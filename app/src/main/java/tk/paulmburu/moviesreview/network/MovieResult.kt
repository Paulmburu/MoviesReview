package tk.paulmburu.moviesreview.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResult(
    @Json(name = "title")val title: String,
    @Json(name = "overview")val overview: String,
    @Json(name = "release_date")val release_date: String,
    @Json(name = "vote_average")val vote_average: String,
    @Json(name = "poster_path")val posterPath: String,
    @Json(name= "original_language")val originalLanguage: String
    ) : Parcelable