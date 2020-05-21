package tk.paulmburu.moviesreview.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException
import tk.paulmburu.moviesreview.interactors.GetAvailablePopularMoviesUseCase
import tk.paulmburu.moviesreview.interactors.GetAvailableUpcomingMoviesUseCase

class RefreshDataWorker(
    private val getAvailableUpcomingMoviesUseCase: GetAvailableUpcomingMoviesUseCase,
    private val getAvailablePopularMoviesUseCase: GetAvailablePopularMoviesUseCase,
    appContext: Context,
    params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    /**
     * A coroutine-friendly method to do your work.
     */
    override suspend fun doWork(): Payload {

        return try {
           getAvailableUpcomingMoviesUseCase.invoke()
            getAvailablePopularMoviesUseCase.invoke()
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }
}
