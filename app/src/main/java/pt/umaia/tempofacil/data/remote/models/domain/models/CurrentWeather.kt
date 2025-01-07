package pt.umaia.tempofacil.data.remote.models.domain.models

import android.health.connect.datatypes.units.Temperature
import pt.umaia.tempofacil.utils.WeatherInfoItem

data class CurrentWeather(
    val temperature: Double,
    val time:String,
    val weatherStatus:WeatherInfoItem,
    val windDirection:String,
    val windSpeed:Double,
    val isDay: Boolean
)