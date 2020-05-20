package tk.paulmburu.moviesreview.ui.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import tk.paulmburu.moviesreview.databinding.FragmentDetailBinding
import tk.paulmburu.moviesreview.viewmodels.ViewModelProviderFactory
import javax.inject.Inject


class DetailFragment : DaggerFragment(){

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        // Get the selectedProperty from the fragment arguments with DetailFragmentArgs
        val movie = DetailFragmentArgs.fromBundle(arguments!!).selectedMovie

        // Get the DetailViewModel from the DetailViewModelFactory and set it in the binding
//        binding.viewModel =
        viewModel = ViewModelProviders.of(
            this, providerFactory).get(DetailViewModel::class.java)
        viewModel.setSelectedMovie(movie)
        return binding.root

    }
}
