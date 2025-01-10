package pt.umaia.tempofacil.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import pt.umaia.tempofacil.R
import pt.umaia.tempofacil.data.FiveResponse
import pt.umaia.tempofacil.data.FiveWeatherResponse
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FiveDaysScreen(
    navController: NavController,
    weatherResponse: FiveResponse?,
    errorMessage: String? = null,
    isLoading: Boolean = false
) {
    Scaffold(
        bottomBar = (

                {
                    NavigationBar(
                        containerColor = (Color(0x80FFFFFF)),
                    ) {

                        // Search
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.mipmap.today_foreground),
                                    contentDescription = "Agenda",
                                    modifier = Modifier.size(50.dp), // Tamanho do ícone ajustado
                                    tint = Color.Unspecified // Desativa a tintagem para preservar a cor original da imagem
                                )
                            },
                            selected = false, // Atualize a lógica para seleção dinâmica
                            onClick = { navController.navigate("home") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                indicatorColor = Color(0xFF006377),
                                unselectedIconColor = Color.LightGray,
                                unselectedTextColor = Color.LightGray
                            )
                        )

                        // Notifications
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.mipmap.forecast_foreground),
                                    contentDescription = "5 Dias",
                                    modifier = Modifier.size(50.dp), // Tamanho do ícone ajustado
                                    tint = Color.Unspecified // Desativa a tintagem para preservar a cor original da imagem
                                )
                            },
                            selected = false, // Atualize a lógica para seleção dinâmica
                            onClick = { navController.navigate("fivedays") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                indicatorColor = Color(0xFF006377),
                                unselectedIconColor = Color.LightGray,
                                unselectedTextColor = Color.LightGray
                            )
                        )

                        // Custom Home Button
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.mipmap.pollution_foreground),
                                    contentDescription = "Poluição do Ar",
                                    modifier = Modifier.size(50.dp),
                                    tint = Color.Unspecified
                                )
                            },
                            selected = false, // Atualize a lógica para seleção dinâmica
                            onClick = { navController.navigate("airpollution") }
                        )
                    }
                }
                )
    ) { innerPadding ->
        Box {
            Image(
                painter = painterResource(id = R.mipmap.wallpaper_foreground),
                contentDescription = "Fundo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    weatherResponse?.let { weather ->
                        WeatherForecastList(weather = weather)
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherForecastList(weather: FiveResponse) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(weather.list.size) { index ->
            WeatherForecastItem(forecast = weather.list[index])
            if (index < weather.list.size - 1) {
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun WeatherForecastItem(forecast: FiveWeatherResponse) {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedDate = sdf.format(Date(forecast.dt * 1000))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color(0x80FFFFFF)) // Fundo com transparência (80 = 50% de opacidade)
            .padding(10.dp)
    ) {
        Text(text = "Data e Hora: $formattedDate", style = MaterialTheme.typography.bodyLarge)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${forecast.weather[0].icon}@2x.png"),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "${forecast.main.temp}°C",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(text = "Descrição: ${forecast.weather[0].description}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Sensação: ${forecast.main.feels_like}°C", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Máxima: ${forecast.main.temp_max}°C | Mínima: ${forecast.main.temp_min}°C", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Vento: ${forecast.wind.speed} km/h", style = MaterialTheme.typography.bodyMedium)
    }
}
