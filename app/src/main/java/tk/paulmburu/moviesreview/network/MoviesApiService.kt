package tk.paulmburu.moviesreview.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.themoviedb.org/3"

//Use Retrofit Builder with ScalarsConverterFactory and BASE_URL
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

//Implement the MoviesApiService interface with @GET getProperties returning a String
interface MoviesApiService {
    @GET("/movie/popular?api_key=3356954909fe5fc6ea425bc2f969dfd8")
    fun getProperties():
            Call<String>
}

//Create the MoviesApi object using Retrofit to implement the MoviesApiService
object MoviesApi {
    val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}