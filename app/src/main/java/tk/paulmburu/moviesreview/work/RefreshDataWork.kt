package tk.paulmburu.moviesreview.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException
import tk.paulmburu.moviesreview.database.getDatabase
import tk.paulmburu.moviesreview.repository.MoviesRepository

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefresahDataWorker"
    }

    /**
     * A coroutine-friendly method to do your work.
     */
    override suspend fun doWork(): Payload {
        val database = getDatabase(applicationContext)
        val repository = MoviesRepository(database)

        return try {
            repository.refreshMovies()
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }
}
