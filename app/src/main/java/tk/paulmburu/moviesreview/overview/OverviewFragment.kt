package tk.paulmburu.moviesreview.overview


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import tk.paulmburu.moviesreview.R
import tk.paulmburu.moviesreview.databinding.FragmentOverviewBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tk.paulmburu.moviesreview.databinding.MovieItemBinding
import tk.paulmburu.moviesreview.domain.Movie


/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {


    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }


        ViewModelProviders.of(this, OverviewViewModel.Factory(activity.application))
            .get(OverviewViewModel::class.java)
    }

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     */
    private var viewModelAdapter: OverviewAdapter? = null
    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<Movie>> { movies ->
            movies?.apply {
                viewModelAdapter?.movies = movies
            }
        })
    }


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

        mSwipeRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            onRefresh()
        })

        mSwipeRefreshLayout!!.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

//        Initialize PhotoGridAdapter with an OnClickListener that calls viewModel.displayMovieDetails
//        Set binding.photosGrid.adapter to a new PhotoGridAdapter()
        viewModelAdapter = OverviewAdapter(OnClickListener {
            viewModel.displayMovieDetails(it)
            Log.d("OVERVIEW--FRAG", "${it.title} clicked")
        })

        binding.root.findViewById<RecyclerView>(R.id.recyclerViewMovies).apply {
            adapter = viewModelAdapter
        }

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

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        inflater?.inflate(R.menu.overflow_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return super.onOptionsItemSelected(item)
//    }
}


/**
import android.widget.Toast
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class OverviewAdapter(val onClickListener: OnClickListener) :  RecyclerView.Adapter<OverviewViewHolder>() {

    /**
     * The Movies that our Adapter will show
     */
    var movies: List<Movie> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        val withDataBinding: MovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            OverviewViewHolder.LAYOUT,
            parent,
            false)
        return OverviewViewHolder(withDataBinding)
    }

    override fun getItemCount() = movies.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.movie = movies.get(position)
            it.clicklistener = onClickListener
        }
    }



}

class OverviewViewHolder(val viewDataBinding: MovieItemBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.movie_item
    }
}

//    Create an OnClickListener class with a lambda in its constructor that initializes a matching onClick function
class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}

