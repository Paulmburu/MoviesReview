package tk.paulmburu.moviesreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tk.paulmburu.moviesreview.database.getDatabase
import tk.paulmburu.moviesreview.databinding.ActivityMainBinding
import tk.paulmburu.moviesreview.repository.MoviesRepository

class MainActivity : AppCompatActivity() {


    private lateinit var moviesRepository: MoviesRepository

    //    Create a coroutine Job and a CoroutineScope using the Main Dispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = getDatabase(this.application)
        moviesRepository = MoviesRepository(database)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item?.itemId) {
            R.id.id_popular_movies_menu -> {
                Toast.makeText(this,"popular_movies_menu", Toast.LENGTH_SHORT).show()
                coroutineScope.launch {
                    moviesRepository.refreshMovies()
                }
                true
            }

            R.id.id_upcoming_movies_menu-> {
                Toast.makeText(this,"upcoming_movies_menu", Toast.LENGTH_SHORT).show()
//                moviesRepository.getUpcomingMovies()
                coroutineScope.launch {
                    moviesRepository.getUpcomingMovies()
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
