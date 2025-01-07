package pt.umaia.tempofacil.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("interval")
    val interval: Int,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("temperature_2m")
    val temperature2m: Double,
    @SerialName("time")
    val time: Int,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("wind_direction_10m")
    val windDirection10m: Int,
    @SerialName("wind_speed_10m")
    val windSpeed10m: Int
)