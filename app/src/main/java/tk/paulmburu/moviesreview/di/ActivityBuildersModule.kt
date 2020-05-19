package tk.paulmburu.moviesreview.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tk.paulmburu.moviesreview.MainActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainInjector(): MainActivity
}