package pt.umaia.tempofacil.data.mapper_impl

import pt.umaia.tempofacil.data.mappers.ApiMapper
import pt.umaia.tempofacil.data.remote.models.ApiHourlyWeather
import pt.umaia.tempofacil.data.remote.models.domain.models.Hourly
import pt.umaia.tempofacil.utils.Util
import pt.umaia.tempofacil.utils.WeatherInfoItem

class ApiHourlyMapper:ApiMapper<Hourly,ApiHourlyWeather> {
    override fun mapToDomain(apiEntity: ApiHourlyWeather): Hourly {
        return Hourly(
            temperature = apiEntity.temperature2m,
            time = parseTime(apiEntity.time),
            weatherStatus = parseWeatherStatus(apiEntity.weatherCode)
        )
    }

    private fun parseTime(time: List<Long>): List<String>{
        return time.map{
            Util.formatUnixDate("HH:mm", it)
        }
    }

    private fun parseWeatherStatus(code: List<Int>): List<WeatherInfoItem>{
        return code.map{
            Util.getWeatherInfo(it)
        }
    }
}