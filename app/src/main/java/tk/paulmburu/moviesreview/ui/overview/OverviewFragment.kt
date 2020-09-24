package tk.paulmburu.moviesreview.ui.overview


import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import tk.paulmburu.moviesreview.R
import tk.paulmburu.moviesreview.databinding.FragmentOverviewBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tk.paulmburu.moviesreview.base.BaseFragment
import tk.paulmburu.moviesreview.databinding.MovieItemBinding
import tk.paulmburu.moviesreview.domain.Movie
import tk.paulmburu.moviesreview.utils.Success
import tk.paulmburu.moviesreview.viewmodels.ViewModelProviderFactory
import javax.inject.Inject


class OverviewFragment : BaseFragment<List<Movie>>(),SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val viewModel: OverviewViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }


        ViewModelProviders.of(this, providerFactory)
            .get(OverviewViewModel::class.java)
    }

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    private var viewModelAdapter: OverviewAdapter? = null


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNetworkChanges()
    }

    private fun subscribeOverflowMenuObserver(){
        viewModel.overFlowMenuState.observe(viewLifecycleOwner, Observer<OverflowMenuState> {
            when(it){
                is PopularMoviesState -> {
                    (activity as AppCompatActivity).supportActionBar?.title = PopularMoviesState().title

                }
                is UpcomingMoviesState -> {
                    (activity as AppCompatActivity).supportActionBar?.title = UpcomingMoviesState().title
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun observeNetworkChanges() {
        onNetworkChange { isConnected ->
            if (isConnected && viewModel.movies.value is Error)
                viewModel.getAvailablePopularMovies()
        }
    }

    override fun onResume() {
        super.onResume()
        observeMovies()
        subscribeOverflowMenuObserver()
    }

    private fun observeMovies() {
        viewModel.movies.observe(this, Observer {
            handleState(it)
        })
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
    }

    override fun showOnSuccess(result: Success<List<Movie>>) {
        super.showOnSuccess(result)
        val availableMovies = result.data
        viewModelAdapter?.movies = availableMovies
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

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

        viewModelAdapter = OverviewAdapter(OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        binding.root.findViewById<RecyclerView>(R.id.recyclerViewMovies).apply {
            adapter = viewModelAdapter
        }

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.id_popular_movies_menu -> {
                viewModel.getAvailablePopularMovies()
                viewModel.setOverflowMenuState(PopularMoviesState())
                true
            }

            R.id.id_upcoming_movies_menu-> {
                viewModel.getAvailableUpcomingMovies()
                viewModel.setOverflowMenuState(UpcomingMoviesState())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
class OverviewAdapter(private val onClickListener: OnClickListener) :  RecyclerView.Adapter<OverviewViewHolder>() {


    var movies: List<Movie> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        val withDataBinding: MovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            OverviewViewHolder.LAYOUT,
            parent,
            false)
        return OverviewViewHolder(withDataBinding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.movie = movies[position]
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

