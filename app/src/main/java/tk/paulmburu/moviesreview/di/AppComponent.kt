package tk.paulmburu.moviesreview.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import tk.paulmburu.moviesreview.MovieApplication

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        DatabaseModule::class,
        ViewModelFactoryModule::class,
        MoviesApiModule::class
    ]
)
interface AppComponent : AndroidInjector<MovieApplication>  {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}