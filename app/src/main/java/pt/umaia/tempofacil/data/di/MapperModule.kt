package pt.umaia.tempofacil.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.umaia.tempofacil.data.mapper_impl.ApiDailyMapper
import pt.umaia.tempofacil.data.mapper_impl.ApiHourlyMapper
import pt.umaia.tempofacil.data.mapper_impl.ApiWeatherMapper
import pt.umaia.tempofacil.data.mapper_impl.CurrentWeatherMapper
import pt.umaia.tempofacil.data.mappers.ApiMapper
import pt.umaia.tempofacil.data.remote.models.ApiCurrentWeather
import pt.umaia.tempofacil.data.remote.models.ApiDailyWeather
import pt.umaia.tempofacil.data.remote.models.ApiHourlyWeather
import pt.umaia.tempofacil.data.remote.models.ApiWeather
import pt.umaia.tempofacil.data.remote.models.domain.models.CurrentWeather
import pt.umaia.tempofacil.data.remote.models.domain.models.Daily
import pt.umaia.tempofacil.data.remote.models.domain.models.Hourly
import pt.umaia.tempofacil.data.remote.models.domain.models.Weather
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @ApiDailyMapperAnnotation
    @Provides
    fun provideDailyApiMapper(): ApiMapper<Daily, ApiDailyWeather> {
        return ApiDailyMapper()
    }
    @ApiCurrentWeatherMapperAnnotation
    @Provides
    fun provideCurrentWeatherMapper(): ApiMapper<CurrentWeather, ApiCurrentWeather> {
        return CurrentWeatherMapper()
    }

    @ApiHourlyWeatherMapperAnnotation
    @Provides
    fun provideHourlyMapper(): ApiMapper<Hourly, ApiHourlyWeather> {
        return ApiHourlyMapper()
    }

    @ApiWeatherMapperAnnotation
    @Provides
    fun provideApiWeatherMapper(
        apiDailyMapper: ApiMapper<Daily, ApiDailyWeather>,
        apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>,
        apiHourlyMapper: ApiMapper<Hourly, ApiHourlyWeather>
    ): ApiMapper<Weather, ApiWeather> {
        return ApiWeatherMapper(
            apiDailyMapper,
            apiCurrentWeatherMapper,
            apiHourlyMapper,
            )
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiDailyMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiWeatherMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiCurrentWeatherMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiHourlyWeatherMapperAnnotation
