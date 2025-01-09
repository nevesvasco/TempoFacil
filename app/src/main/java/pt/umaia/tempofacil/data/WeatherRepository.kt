package pt.umaia.tempofacil.data

import javax.inject.Inject

class WeatherRepository @Inject constructor(
    public val apiService: WeatherApiService
) {

    suspend fun getWeatherData(lat: Double, lon: Double, apiKey: String): WeatherResponse {
        return apiService.getDailyWeather(lat, lon, apiKey)
    }
}