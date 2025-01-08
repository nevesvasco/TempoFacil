package pt.umaia.tempofacil.data.mapper_impl

import pt.umaia.tempofacil.data.di.ApiCurrentWeatherMapperAnnotation
import pt.umaia.tempofacil.data.di.ApiDailyMapperAnnotation
import pt.umaia.tempofacil.data.di.ApiHourlyWeatherMapperAnnotation
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
    @ApiDailyMapperAnnotation val apiDailyMapper: ApiMapper<Daily, ApiDailyWeather>,
    @ApiCurrentWeatherMapperAnnotation val apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>,
    @ApiHourlyWeatherMapperAnnotation val apiHourlyMapper: ApiMapper<Hourly, ApiHourlyWeather>
): ApiMapper<Weather, ApiWeather> {
    override fun mapToDomain(apiEntity: ApiWeather): Weather {
        return Weather(
            currentWeather = apiCurrentWeatherMapper.mapToDomain(apiEntity.current),
            daily = apiDailyMapper.mapToDomain(apiEntity.daily),
            hourly = apiHourlyMapper.mapToDomain(apiEntity.hourly)
        )
    }
}