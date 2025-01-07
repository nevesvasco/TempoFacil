package pt.umaia.tempofacil.data.mapper_impl

import pt.umaia.tempofacil.data.mappers.ApiMapper
import pt.umaia.tempofacil.data.remote.models.ApiCurrentWeather
import pt.umaia.tempofacil.data.remote.models.ApiDailyWeather
import pt.umaia.tempofacil.data.remote.models.ApiHourlyWeather
import pt.umaia.tempofacil.data.remote.models.ApiWeather
import pt.umaia.tempofacil.data.remote.models.domain.models.CurrentWeather
import pt.umaia.tempofacil.data.remote.models.domain.models.Daily
import pt.umaia.tempofacil.data.remote.models.domain.models.Hourly
import pt.umaia.tempofacil.data.remote.models.domain.models.Weather
import pt.umaia.tempofacil.utils.WeatherInfoItem
import javax.inject.Inject

class ApiWeatherMapper @Inject constructor(
    private val apiDailyMapper: ApiMapper<Daily, ApiDailyWeather>,
    private val apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>,
    private val apiHourlyWeather: ApiMapper<Hourly, ApiHourlyWeather>
): ApiMapper<Weather, ApiWeather> {
    override fun mapToDomain(apiEntity: ApiWeather): Weather {
        return Weather(
            currentWeather = apiCurrentWeatherMapper.mapToDomain(apiEntity.current),
            daily = apiDailyMapper.mapToDomain(apiEntity.daily),
            hourly = apiHourlyWeather.mapToDomain(apiEntity.hourly)
        )
    }
}