package tk.paulmburu.moviesreview.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tk.paulmburu.moviesreview.MainActivity
import tk.paulmburu.moviesreview.di.main.MainFragmentsBuildersModule
import tk.paulmburu.moviesreview.di.main.MainModule
import tk.paulmburu.moviesreview.di.main.MainViewModelsModule

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [
        MainFragmentsBuildersModule::class,
            MainModule::class,
        MainViewModelsModule::class
        ]
    )
    abstract fun contributeMainInjector(): MainActivity
}