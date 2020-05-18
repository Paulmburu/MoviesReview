package tk.paulmburu.moviesreview

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import tk.paulmburu.moviesreview.work.RefreshDataWorker
import java.util.concurrent.TimeUnit

/**
 * Override application to setup background work via WorkManager
 */
class MovieApplication : Application(){
    // CoroutineScope variable applicationScope, using Dispatchers.Default.
    val applicationScope = CoroutineScope(Dispatchers.Default)

    // delayedInit() function that calls setupRecurringWork() in
    // the coroutine you defined above.
    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }
    // setupRecurringWork() function and use a Builder to define a
    // repeatingRequest variable to handle scheduling work.
    // setupRecurringWork(), get an instance of WorkManager and
    // launch call enqueuPeriodicWork() to schedule the work
    private fun setupRecurringWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    // in setupRecurringWork(), define constraints to prevent work from occurring when
    // there is no network access or the device is low on battery.
    //  Add the constraints to the repeatingRequest definition.
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresBatteryNotLow(true)
        .setRequiresCharging(true)
        .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setRequiresDeviceIdle(true)
            }
        }.build()


    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // Call delayedInit().
        val delayedInit = delayedInit()
    }
}