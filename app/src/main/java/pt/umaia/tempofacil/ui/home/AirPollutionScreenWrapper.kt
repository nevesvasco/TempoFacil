package pt.umaia.tempofacil.ui.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.umaia.tempofacil.data.PollutionResponse
import pt.umaia.tempofacil.data.PollutionWeatherResponse
import pt.umaia.tempofacil.data.WeatherRepository
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun AirPollutionScreenWrapper(
    navController: NavController,
    weatherRepository: WeatherRepository
) {
    val isLoading = remember { mutableStateOf(true) }
    val airPollutionResponse = remember { mutableStateOf<PollutionResponse?>(null) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    suspend fun fetchAirPollutionHttp(lat: Double, lon: Double, apiKey: String): PollutionResponse? {
        val urlString =
            "http://api.openweathermap.org/data/2.5/air_pollution?lat=$lat&lon=$lon&appid=$apiKey"
        Log.d("AirPollution", "Fetching air pollution data from URL: $urlString")
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("AirPollution", "Received response: $response")
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(PollutionResponse::class.java)
                adapter.fromJson(response)
            } else {
                Log.e("AirPollution", "Error: HTTP response code ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            Log.e("AirPollution", "Exception during HTTP request: ${e.message}", e)
            null
        }
    }

    suspend fun fetchAirPollution() {
        try {
            val apiKey = "0f56b5eaf34aa173d7ad2c9cc2a3cb01"
            val lat = 41.328279
            val lon = -8.550500

            Log.d("AirPollution", "Fetching air pollution data for lat=$lat, lon=$lon")
            val response = fetchAirPollutionHttp(lat, lon, apiKey)
            airPollutionResponse.value = response
            Log.d("AirPollution", "Air pollution data fetched successfully.")
        } catch (e: Exception) {
            Log.e("AirPollution", "Exception during fetchAirPollution: ${e.message}", e)
            errorMessage.value = e.message
        }
    }

    LaunchedEffect(Unit) {
        try {
            val apiKey = "0f56b5eaf34aa173d7ad2c9cc2a3cb01"
            val lat = 41.328279
            val lon = -8.550500
            fetchAirPollution()
            airPollutionResponse.value = weatherRepository.apiService.getPollutionWeather(lat, lon, apiKey)

            isLoading.value = false

        } catch (e: Exception) {
            Log.e("AirPollution", "Exception during LaunchedEffect: ${e.message}", e)
            errorMessage.value = e.message
            isLoading.value = false
        }
    }

    AirPollutionScreen(
        navController,
        airPollutionData = airPollutionResponse.value,
        errorMessage = errorMessage.value,
        isLoading = isLoading.value
    )
}
