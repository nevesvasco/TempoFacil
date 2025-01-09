package pt.umaia.tempofacil.ui.home

import pt.umaia.tempofacil.data.DailyWeatherData

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.umaia.tempofacil.data.WeatherRepository
import pt.umaia.tempofacil.data.WeatherResponse
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var homeState = mutableStateOf(HomeState())
        private set

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            try {
                homeState.value = homeState.value.copy(isLoading = true)
                val response = repository.getWeatherData(
                    lat = 40.6405, // Latitude do Porto, por exemplo.
                    lon = -8.6538, // Longitude do Porto.
                    apiKey = "0f56b5eaf34aa173d7ad2c9cc2a3cb01"
                )
                homeState.value = homeState.value.copy(
                    isLoading = false,
                    weather = response
                )
            } catch (e: Exception) {
                homeState.value = homeState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}


data class HomeState(
    val weather: WeatherResponse? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val dailyWeatherInfo: List<DailyWeatherData> = emptyList()
)
