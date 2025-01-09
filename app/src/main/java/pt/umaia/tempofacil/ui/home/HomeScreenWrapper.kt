package pt.umaia.tempofacil.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import pt.umaia.tempofacil.data.WeatherRepository
import pt.umaia.tempofacil.data.WeatherResponse
import pt.umaia.tempofacil.data.HourlyWeatherData
import pt.umaia.tempofacil.data.CurrentWeatherData
import pt.umaia.tempofacil.data.DailyWeatherData
import java.lang.Exception


@Composable
fun HomeScreenWrapper(
    navController: NavController,
    weatherRepository: WeatherRepository
) {
    // Estados para controlar os dados e mensagens
    val isLoading = remember { mutableStateOf(true) }
    val weatherResponse = remember { mutableStateOf<WeatherResponse?>(null) }
    val hourlyWeather = remember { mutableStateOf<List<HourlyWeatherData>>(emptyList()) }
    val currentWeather = remember { mutableStateOf<CurrentWeatherData?>(null) }
    val dailyWeatherInfo = remember { mutableStateOf<List<DailyWeatherData>>(emptyList()) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Recuperar dados de previsão do tempo
    LaunchedEffect(Unit) {
        try {
            val apiKey = "0f56b5eaf34aa173d7ad2c9cc2a3cb01" // Sua chave de API
            val lat = 40.6405
            val lon = -8.6538

            // Chamada para pegar o clima atual
            currentWeather.value = weatherRepository.apiService.getCurrentWeather(lat, lon, apiKey)

            // Chamada para pegar as previsões horárias
            hourlyWeather.value = weatherResponse.value?.hourly ?: emptyList()

            // Chamada para pegar as previsões diárias
            weatherResponse.value = weatherRepository.apiService.getDailyWeather(lat, lon, apiKey)

            // Extraindo as informações diárias do response
            dailyWeatherInfo.value = weatherResponse.value?.daily ?: emptyList()

            // Atualizar estado de carregamento
            isLoading.value = false
        } catch (e: Exception) {
            // Exibir mensagem de erro em caso de falha
            errorMessage.value = e.message
            isLoading.value = false
        }
    }

    // Passar os dados para a HomeScreen
    HomeScreen(
        navController = navController,
        weatherResponse = weatherResponse.value,
        hourlyWeather = hourlyWeather.value,
        currentWeather = currentWeather.value,
        dailyWeatherInfo = dailyWeatherInfo.value,
        errorMessage = errorMessage.value,
        isLoading = isLoading.value
    )
}