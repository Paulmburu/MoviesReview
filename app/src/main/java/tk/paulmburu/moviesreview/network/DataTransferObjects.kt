package tk.paulmburu.moviesreview.network

import com.squareup.moshi.JsonClass
import tk.paulmburu.moviesreview.database.PopularMovies
import tk.paulmburu.moviesreview.database.UpcomingMovies
import tk.paulmburu.moviesreview.domain.Movie

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */

// that returns an array of <DatabaseMovieResult>.
fun NetworkMovieContainer.asDatabasePopularMoviesModel(): Array<PopularMovies> {
    return results.map {
        PopularMovies (
            title = it.title,
            overview = it.overview,
            release_date = it.release_date,
            vote_average = it.vote_average,
            poster_path = it.poster_path,
            original_language = it.original_language)
    }.toTypedArray()
}

fun NetworkMovieContainer.asDatabaseUpcomingMoviesModel(): Array<UpcomingMovies> {
    return results.map {
        UpcomingMovies (
            title = it.title,
            overview = it.overview,
            release_date = it.release_date,
            vote_average = it.vote_average,
            poster_path = it.poster_path,
            original_language = it.original_language)
    }.toTypedArray()
}
/**
 * MovieHolder holds a list of Movies.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "results": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val  results : List<NetworkMovie>)

/**
 * Movies represent a movie that can be displayed.
 */
@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val title: String,
    val overview: String,
    val release_date: String,
    val vote_average: String,
    val poster_path: String,
    val original_language: String)

/**
 * Convert Network results to database objects
 */
fun NetworkMovieContainer.asDomainModel(): List<Movie> {
    return results.map {
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