package pt.umaia.tempofacil.ui.home

import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.squareup.moshi.Moshi
import pt.umaia.tempofacil.data.FiveResponse
import pt.umaia.tempofacil.data.WeatherRepository
import pt.umaia.tempofacil.data.WeatherResponse
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun FiveDaysScreenWrapper(
    navController: NavController,
    weatherRepository: WeatherRepository
) {
    val isLoading = remember { mutableStateOf(true) }
    val weatherResponse = remember { mutableStateOf<FiveResponse?>(null) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    suspend fun fetchFiveDayForecastHttp(lat: Double, lon: Double, apiKey: String): FiveResponse? {
        val urlString =
            "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&appid=$apiKey"

        Log.d("WeatherDebug", "Making request to URL: $urlString")  // Log URL

        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            Log.d("WeatherDebug", "Response Code: ${connection.responseCode}")  // Log Response Code

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("WeatherDebug", "Response: $response")  // Log Response

                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(FiveResponse::class.java)
                val parsedResponse = adapter.fromJson(response)

                if (parsedResponse != null) {
                    Log.d("WeatherDebug", "Parsed Response: $parsedResponse")  // Log Parsed Response
                }

                parsedResponse
            } else {
                Log.e("WeatherDebug", "Error: HTTP response code ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            Log.e("WeatherDebug", "Error fetching forecast: ${e.message}", e)  // Log Error
            null
        }
    }

    suspend fun fetchWeatherForecast() {
        try {
            val apiKey = "0f56b5eaf34aa173d7ad2c9cc2a3cb01"
            val lat = 41.328279
            val lon = -8.550500

            Log.d("WeatherDebug", "Fetching weather forecast...")  // Log fetch action
            val response = fetchFiveDayForecastHttp(lat, lon, apiKey)
            weatherResponse.value = response

            if (response == null) {
                errorMessage.value = "Failed to fetch weather data"
            }
        } catch (e: Exception) {
            Log.e("WeatherDebug", "Error in fetchWeatherForecast: ${e.message}", e)  // Log Error
            errorMessage.value = e.message
        }
    }

    LaunchedEffect(Unit) {
        try {
            val apiKey = "0f56b5eaf34aa173d7ad2c9cc2a3cb01"
            val lat = 41.328279
            val lon = -8.550500

            fetchWeatherForecast()

            weatherResponse.value = weatherRepository.apiService.getFiveWeather(lat, lon, apiKey)

            isLoading.value = false
        } catch (e: java.lang.Exception) {
            println("Exception during LaunchedEffect: ${e.message}")
            e.printStackTrace()
            errorMessage.value = e.message
            isLoading.value = false
        }
    }

    // Log when screen is rendered
    Log.d("WeatherDebug", "Rendering FiveDaysScreen")

    FiveDaysScreen(
        navController = navController,
        weatherResponse = weatherResponse.value,
        errorMessage = errorMessage.value,
        isLoading = isLoading.value
    )
}
