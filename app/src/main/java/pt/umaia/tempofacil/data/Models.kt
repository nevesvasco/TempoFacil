package pt.umaia.tempofacil.data

import com.squareup.moshi.Json


data class WeatherResponse(
    @Json(name = "current") val current: CurrentWeatherResponse,
    @Json(name = "daily") val daily: List<DailyWeatherData>,
    @Json(name = "hourly") val hourly: List<HourlyWeatherData>
)

data class CurrentWeatherResponse(
    val main: Main,
    val weather: List<WeatherDescription>,
    val wind: Wind,
    val name: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class DailyWeatherData(
    @Json(name = "dt") val time: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
    @Json(name = "uvi") val uvIndex: Double,
    @Json(name = "weather") val weather: List<WeatherDescription>
)

data class HourlyWeatherData(
    @Json(name = "dt") val time: Long,
    @Json(name = "temp") val temperature: Double,
    @Json(name = "weather") val weather: List<WeatherDescription>
)
