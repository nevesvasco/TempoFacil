package pt.umaia.tempofacil.data.remote.models.domain.models

import pt.umaia.tempofacil.data.remote.models.ApiDailyWeather

data class Weather(
    val currentWeather: CurrentWeather,
    val daily: Daily,
    val hourly: Hourly
)