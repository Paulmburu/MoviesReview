package tk.paulmburu.moviesreview.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import tk.paulmburu.moviesreview.models.MoviesProperties

private const val BASE_URL = "https://api.themoviedb.org/3/"

//Moshi Builder to create a Moshi object with the KotlinJsonAdapterFactory
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

//Implement the MoviesApiService interface with @GET getProperties returning a String
interface MoviesApiService {
    @GET("movie/popular?api_key=3356954909fe5fc6ea425bc2f969dfd8")
    fun getProperties():
            Deferred<MoviesProperties>
}

//Create the MoviesApi object using Retrofit to implement the MoviesApiService
object MoviesApi {
    val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}