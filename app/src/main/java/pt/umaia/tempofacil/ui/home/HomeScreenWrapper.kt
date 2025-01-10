package pt.umaia.tempofacil.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.squareup.moshi.Moshi
import pt.umaia.tempofacil.data.WeatherRepository
import pt.umaia.tempofacil.data.WeatherResponse
import java.lang.Exception
import pt.umaia.tempofacil.data.CurrentWeatherResponse
import java.net.HttpURLConnection
import java.net.URL


@Composable
fun HomeScreenWrapper(
    navController: NavController,
    weatherRepository: WeatherRepository
) {
    val isLoading = remember { mutableStateOf(true) }
    val weatherResponse = remember { mutableStateOf<WeatherResponse?>(null) }
    val currentWeather = remember { mutableStateOf<CurrentWeatherResponse?>(null) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    suspend fun fetchCurrentWeatherHttp(lat: Double, lon: Double, apiKey: String): CurrentWeatherResponse? {
        val urlString =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric&lang=pt"
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(CurrentWeatherResponse::class.java)
                adapter.fromJson(response)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchCurrentWeather() {
        try {
            val apiKey = "0f56b5eaf34aa173d7ad2c9cc2a3cb01"
            val lat = 40.6405
            val lon = -8.6538

            val response = fetchCurrentWeatherHttp(lat, lon, apiKey)
            currentWeather.value = response

        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage.value = e.message
        }
    }

    LaunchedEffect(Unit) {
        try {
            val apiKey = "0f56b5eaf34aa173d7ad2c9cc2a3cb01"
            val lat = 41.328279
            val lon = -8.550500

            fetchCurrentWeather()

            currentWeather.value = weatherRepository.apiService.getCurrentWeather(lat, lon, apiKey)

            isLoading.value = false
        } catch (e: Exception) {
            println("Exception during LaunchedEffect: ${e.message}")
            e.printStackTrace()
            errorMessage.value = e.message
            isLoading.value = false
        }
    }

    HomeScreen(
        navController = navController,
        currentWeather = currentWeather.value,
        errorMessage = errorMessage.value,
        isLoading = isLoading.value
    )
}
