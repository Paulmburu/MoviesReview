package tk.paulmburu.moviesreview.overview


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import tk.paulmburu.moviesreview.R
import tk.paulmburu.moviesreview.databinding.FragmentOverviewBinding
//import tk.paulmburu.moviesreview.databinding.FragmentOverviewBinding
import tk.paulmburu.moviesreview.databinding.MovieItemBinding

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

//        Set binding.photosGrid.adapter to a new PhotoGridAdapter()
        binding.recyclerViewMovies.adapter = PhotoGridAdapter()


        return binding.root
    }


}
