package tk.paulmburu.moviesreview.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import tk.paulmburu.moviesreview.domain.Movie

@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val title: String,
    val overview: String,
    val release_date: String,
    val vote_average: String,
    val poster_path: String,
    val original_language: String
)

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            title = it.title,
            overview = it.overview,
            release_date = it.release_date,
            vote_average = it.vote_average,
            poster_path = it.poster_path,
            original_language = it.original_language
        )
    }
}