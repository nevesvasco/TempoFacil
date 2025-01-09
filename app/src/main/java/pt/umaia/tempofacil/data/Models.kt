package pt.umaia.tempofacil.data

import com.squareup.moshi.Json


data class WeatherResponse(
    @Json(name = "current") val current: CurrentWeatherData,
    @Json(name = "daily") val daily: List<DailyWeatherData>,
    @Json(name = "hourly") val hourly: List<HourlyWeatherData>
)

data class CurrentWeatherData(
    @Json(name = "temp") val temperature: Double,
    @Json(name = "wind_speed") val windSpeed: Double,
    @Json(name = "weather") val weather: List<WeatherDescription>
)

data class WeatherDescription(
    @Json(name = "icon") val icon: String,
    @Json(name = "description") val description: String
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
