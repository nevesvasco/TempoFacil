package pt.umaia.tempofacil.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.umaia.tempofacil.data.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)  // Hilt irá fornecer a dependência no escopo singleton
object WeatherApiModule {

    @Provides
    fun provideWeatherApiService(): WeatherApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")  // Defina sua base URL
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val apiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
        return apiService
    }
}