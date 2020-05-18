package tk.paulmburu.moviesreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tk.paulmburu.moviesreview.database.getDatabase
import tk.paulmburu.moviesreview.databinding.ActivityMainBinding
import tk.paulmburu.moviesreview.repository.MoviesRepository

class MainActivity : AppCompatActivity() {


    private lateinit var moviesRepository: MoviesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph )

        findViewById<Toolbar>(R.id.my_toolbar)
            .setupWithNavController(navController, appBarConfiguration)

    }

}
