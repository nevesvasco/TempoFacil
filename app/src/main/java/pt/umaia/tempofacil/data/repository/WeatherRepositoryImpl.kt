package pt.umaia.tempofacil.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import pt.umaia.tempofacil.data.mapper_impl.ApiWeatherMapper
import pt.umaia.tempofacil.data.remote.WeatherApi
import pt.umaia.tempofacil.data.remote.models.domain.models.Weather
import pt.umaia.tempofacil.data.remote.models.domain.repository.WeatherRepository
import pt.umaia.tempofacil.utils.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val apiWeatherMapper: ApiWeatherMapper
):WeatherRepository{
    override fun getWeatherData(): Flow<Response<Weather>> = flow {
        emit(Response.Loading())
        val apiWeather = weatherApi.getWeatherData()
        val weather = apiWeatherMapper.mapToDomain(apiWeather)
        emit(Response.Success(weather))
    }.catch { e ->
        e.printStackTrace()
        emit(Response.Error(e.message.orEmpty()))
    }
}