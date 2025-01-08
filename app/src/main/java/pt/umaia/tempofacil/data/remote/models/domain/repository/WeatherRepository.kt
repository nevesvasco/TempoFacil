package pt.umaia.tempofacil.data.remote.models.domain.repository

import kotlinx.coroutines.flow.Flow
import pt.umaia.tempofacil.data.remote.models.domain.models.Weather
import pt.umaia.tempofacil.utils.Response

interface WeatherRepository {

    fun getWeatherData() : Flow<Response<Weather>>
}