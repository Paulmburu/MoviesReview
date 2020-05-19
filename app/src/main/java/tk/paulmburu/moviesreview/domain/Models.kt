package tk.paulmburu.moviesreview.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Movie(
    val title: String,
    val overview: String,
    val release_date: String,
    val vote_average: String,
    val poster_path: String?,
    val original_language: String
) : Parcelable

