package tk.paulmburu.moviesreview.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tk.paulmburu.moviesreview.database.MovieDao
import tk.paulmburu.moviesreview.database.MoviesDatabase
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.network.MoviesApiService
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}