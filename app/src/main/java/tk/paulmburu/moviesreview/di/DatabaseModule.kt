package tk.paulmburu.moviesreview.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import tk.paulmburu.moviesreview.database.MovieDao
import tk.paulmburu.moviesreview.database.MoviesDatabase

@Module
class DatabaseModule {
    @Provides
    fun provideRoomDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(context.applicationContext,
            MoviesDatabase::class.java,
            "movies")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(moviesDatabase: MoviesDatabase): MovieDao {
        return moviesDatabase.movieDao
    }
}