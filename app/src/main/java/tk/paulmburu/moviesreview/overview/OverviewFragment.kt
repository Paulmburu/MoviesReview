package tk.paulmburu.moviesreview.overview


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import tk.paulmburu.moviesreview.R
import tk.paulmburu.moviesreview.databinding.FragmentOverviewBinding
//import tk.paulmburu.moviesreview.databinding.FragmentOverviewBinding
import tk.paulmburu.moviesreview.databinding.MovieItemBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_overview.*


/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {


    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        mSwipeRefreshLayout = binding.swipeContainer

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

//        Initialize PhotoGridAdapter with an OnClickListener that calls viewModel.displayMovieDetails
//        Set binding.photosGrid.adapter to a new PhotoGridAdapter()
        binding.recyclerViewMovies.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

//        Observe navigateToSelectedMovie, Navigate when MovieResult !null, then call displayMovieDetailsComplete()
        viewModel.navigateToSelectedMovie.observe(this, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayMovieDetailsComplete()
            }
        })


        return binding.root
    }

    override fun onRefresh() {
        viewModel.onSwipe()
        mSwipeRefreshLayout!!.isRefreshing = false
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
            }
        return true
    }
}
