package tk.paulmburu.moviesreview.di.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tk.paulmburu.moviesreview.ui.details.DetailFragment
import tk.paulmburu.moviesreview.ui.overview.OverviewFragment

@Module
abstract class MainFragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeOverviewFragment(): OverviewFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
}