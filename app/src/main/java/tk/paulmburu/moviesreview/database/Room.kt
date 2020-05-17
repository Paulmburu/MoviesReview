package tk.paulmburu.moviesreview.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    // SQL @Query getMovies() function that returns a List of DatabaseMovieResult
    @Query("select * from popularmovies")
    fun getPopularMovies(): List<PopularMovies>

    // SQL @Insert insertAll() that replaces on conflict (or upsert).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPopularMovies(vararg videos: PopularMovies)

    // SQL @Query deleteMovies() function that deletes all movies
    @Query("DELETE FROM popularmovies")
    fun deleteAllPopularMovies()

    // SQL @Query getUpcomingMovies() function that returns a List of DatabaseMovieResult
    @Query("select * from upcomingmovies")
    fun getUpcomingMovies(): List<UpcomingMovies>

    // SQL @Insert insertAllUpcomingMovies() that replaces on conflict (or upsert).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUpcomingMovies(vararg videos: UpcomingMovies)

    // SQL @Query deleteUpcomingMovies() function that deletes all movies
    @Query("DELETE FROM upcomingmovies")
    fun deleteAllUpcomingMovies()
}

// An abstract MoviesDatabase class that extends RoomDatabase.
@Database(entities = [PopularMovies::class, UpcomingMovies::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}


private lateinit var INSTANCE: MoviesDatabase

// getDatabase() returns the MoviesDatabase INSTANCE
fun getDatabase(context: Context): MoviesDatabase {
    synchronized(MoviesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MoviesDatabase::class.java,
                "videos").build()

        }
    }
    return INSTANCE

}