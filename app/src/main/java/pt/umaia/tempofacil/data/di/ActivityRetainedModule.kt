package pt.umaia.tempofacil.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import pt.umaia.tempofacil.data.remote.models.domain.repository.WeatherRepository
import pt.umaia.tempofacil.data.repository.WeatherRepositoryImpl

@Module
@InstallIn(ActivityRetainedComponent :: class)
abstract class ActivityRetainedModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bundWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl):WeatherRepository
}