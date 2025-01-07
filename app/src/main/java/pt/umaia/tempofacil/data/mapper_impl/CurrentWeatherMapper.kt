package pt.umaia.tempofacil.data.mapper_impl

import android.graphics.Path.Direction
import pt.umaia.tempofacil.data.mappers.ApiMapper
import pt.umaia.tempofacil.data.remote.models.ApiCurrentWeather
import pt.umaia.tempofacil.data.remote.models.domain.models.CurrentWeather
import pt.umaia.tempofacil.data.remote.models.domain.models.Daily
import pt.umaia.tempofacil.utils.Util
import pt.umaia.tempofacil.utils.WeatherInfoItem

class CurrentWeatherMapper:ApiMapper<CurrentWeather, ApiCurrentWeather> {
    override fun mapToDomain(apiEntity: ApiCurrentWeather): CurrentWeather {
        return CurrentWeather(
            temperature = apiEntity.temperature2m,
            time = parseTime(apiEntity.time),
            weatherStatus = parseWeatherStatus(apiEntity.weatherCode),
            windDirection = parseWindDirection(apiEntity.windDirection10m),
            windSpeed = apiEntity.windSpeed10m,
            isDay = apiEntity.isDay == 1
        )
    }
    private fun parseTime(time:Long):String{
        return Util.formatUnixDate("MMM,d", time)
    }

    private fun parseWeatherStatus(code:Int): WeatherInfoItem {
        return Util.getWeatherInfo(code)
    }

    private fun parseWindDirection(windDirection: Double):String{
        return Util.getWindDirection(windDirection)
    }
}