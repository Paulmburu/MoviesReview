package tk.paulmburu.moviesreview.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    // SQL @Query getMovies() function that returns a List of DatabaseMovieResult livedata.
    @Query("select * from databasemovie")
    fun getMovies(): LiveData<List<DatabaseMovie>>

    // SQL @Insert insertAll() that replaces on conflict (or upsert).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseMovie)

    // SQL @Query deleteMovies() function that deletes all movies
    @Query("DELETE FROM databasemovie")
    fun deleteAllMovies()
}

// An abstract MoviesDatabase class that extends RoomDatabase.
@Database(entities = [DatabaseMovie::class], version = 1)
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