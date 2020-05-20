package tk.paulmburu.moviesreview.network


import retrofit2.http.GET
import tk.paulmburu.moviesreview.BuildConfig

//Implement the MoviesApiService interface with @GET getProperties returning a String
interface MoviesApiService {
    @GET("movie/popular?api_key=${BuildConfig.MOVIESREVIEW_API_KEY}")
    suspend fun getPopularMovies(): NetworkMovieContainer

    @GET("movie/upcoming?api_key=${BuildConfig.MOVIESREVIEW_API_KEY}")
    suspend fun getUpcomingMovies(): NetworkMovieContainer

}
