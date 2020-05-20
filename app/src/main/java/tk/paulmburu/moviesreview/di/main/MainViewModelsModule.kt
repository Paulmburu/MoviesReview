package tk.paulmburu.moviesreview.di.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import tk.paulmburu.moviesreview.di.ViewModelKey
import tk.paulmburu.moviesreview.ui.details.DetailViewModel
import tk.paulmburu.moviesreview.ui.overview.OverviewViewModel

@Module
abstract class  MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(OverviewViewModel::class)
    abstract fun bindOverviewViewModel(viewModel: OverviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel
}