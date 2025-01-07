package pt.umaia.tempofacil.data.remote

import pt.umaia.tempofacil.data.remote.models.ApiWeather
import pt.umaia.tempofacil.utils.ApiParameters
import pt.umaia.tempofacil.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(K.END_POINT)
    suspend fun getWeatherData(
        @Query(ApiParameters.LATITUDE) latitude:Float = -6.8f,
        @Query(ApiParameters.LONGITUDE) longitude:Float = 39.28f,
        @Query(ApiParameters.DAILY) daily:Array<String> = arrayOf(
            "weather_code",
            "temperature_2m_max",
            "temperature_2m_min",
            "wind_speed_10m_max",
            "wind_direction_10m_dominant",
            "sunrise",
            "sunset",
            "uv_index_max",
        ),
        @Query(ApiParameters.CURRENT_WEATHER) currentWeather: Array<String> = arrayOf(
            "temperature_2m",
            "is_day",
            "weather_code",
            "wind_speed_10m",
            "wind_direction_10m",
        ),
        @Query(ApiParameters.HOURLY) hourlyWeather: Array<String> = arrayOf(
            "weather_code",
            "temperature_2m",
        ),
        @Query(ApiParameters.TIME_FORMAT) timeformat: String = "unixtime",
        @Query(ApiParameters.TIMEZONE) timeZone: String = "Europe/London",
    ): ApiWeather
}