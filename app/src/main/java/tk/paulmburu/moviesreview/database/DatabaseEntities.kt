package tk.paulmburu.moviesreview.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import tk.paulmburu.moviesreview.domain.Movie

sealed class DatabaseEntities(){
    abstract val title: String
    abstract val overview: String
    abstract val release_date: String
    abstract val vote_average: String
    abstract val poster_path: String
    abstract val original_language: String
}

@Entity
data class PopularMovies constructor(
    @PrimaryKey
    override val title: String,
    override val overview: String,
    override val release_date: String,
    override val vote_average: String,
    override val poster_path: String,
    override val original_language: String
) : DatabaseEntities()

@Entity
data class UpcomingMovies constructor(
    @PrimaryKey
    override val title: String,
    override val overview: String,
    override val release_date: String,
    override val vote_average: String,
    override val poster_path: String,
    override val original_language: String
) : DatabaseEntities()


fun List<DatabaseEntities>.asDomainModel(): List<Movie> {
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