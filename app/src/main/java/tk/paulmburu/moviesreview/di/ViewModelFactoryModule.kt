package tk.paulmburu.moviesreview.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import tk.paulmburu.moviesreview.viewmodels.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}