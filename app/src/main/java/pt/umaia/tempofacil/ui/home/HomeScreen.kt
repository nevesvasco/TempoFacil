package pt.umaia.tempofacil.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import pt.umaia.tempofacil.data.CurrentWeatherData
import pt.umaia.tempofacil.data.DailyWeatherData
import pt.umaia.tempofacil.data.HourlyWeatherData
import pt.umaia.tempofacil.data.WeatherResponse
import pt.umaia.tempofacil.utils.Util
import java.text.SimpleDateFormat
import java.util.*

const val degreeTxt = "°"

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    weatherResponse: WeatherResponse? = null,  // Passando os dados diretamente
    hourlyWeather: List<HourlyWeatherData> = emptyList(),  // Passando os dados de previsão horária diretamente
    currentWeather: CurrentWeatherData? = null, // Passando os dados de previsão atual diretamente
    dailyWeatherInfo: List<DailyWeatherData> = emptyList(), // Passando os dados diários
    errorMessage: String? = null, // Passando a mensagem de erro diretamente
    isLoading: Boolean = false  // Indicando o estado de carregamento
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            if (isLoading) {
                // Se estiver carregando, podemos mostrar um indicador de progresso
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                currentWeather?.let { weather ->
                    CurrentWeatherItem(
                        currentWeather = weather,
                    )
                }

                hourlyWeather?.let { hourly ->
                    HourlyWeatherItem(
                        hourly = hourly,
                    )
                }

                // Passando a lista dailyWeatherInfo individualmente
                if (dailyWeatherInfo.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp) // Espaçamento entre os itens
                    ) {
                        items(dailyWeatherInfo) { weatherItem ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                SunSetWeatherItem(weatherInfo = weatherItem)
                                UvIndexWeatherItem(weatherInfo = weatherItem)
                            }
                        }
                    }
                }

                // Se houver erro, exibe a mensagem
                errorMessage?.let {
                    Text(text = it, color = androidx.compose.ui.graphics.Color.Red)
                }
            }
        }
    }
}


@Composable
fun CurrentWeatherItem(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeatherData
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${currentWeather.weather[0].icon}@2x.png"),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = "${currentWeather.temperature}$degreeTxt",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = currentWeather.weather[0].description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun HourlyWeatherItem(
    modifier: Modifier = Modifier,
    hourly: List<HourlyWeatherData> // Corrigido para esperar uma lista
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Today",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = Util.formatNormalDate("MMMM, dd", Date().time),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LazyRow(
                modifier = Modifier.padding(16.dp)
            ) {
                items(hourly) { infoItem -> // Itera sobre a lista corretamente
                    HourlyWeatherInfoItem(infoItem = infoItem)
                }
            }
        }
    }
}


@Composable
fun HourlyWeatherInfoItem(
    modifier: Modifier = Modifier,
    infoItem: HourlyWeatherData
) {
    Column(
        modifier = modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${infoItem.temperature}$degreeTxt",
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${infoItem.weather[0].icon}@2x.png"),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = formatTime(infoItem.time),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun SunSetWeatherItem(
    modifier: Modifier = Modifier,
    weatherInfo: DailyWeatherData
) {
    Card(modifier = Modifier.padding(horizontal = 8.dp)) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sunrise",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = formatTime(weatherInfo.sunrise),
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = "Sunset: ${formatTime(weatherInfo.sunset)}",
                style = MaterialTheme.typography.displayMedium,
            )
        }
    }
}

@Composable
fun UvIndexWeatherItem(
    modifier: Modifier = Modifier,
    weatherInfo: DailyWeatherData
) {
    Card(modifier = Modifier.padding(horizontal = 8.dp)) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "UV INDEX",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = weatherInfo.uvIndex.toString(),
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = "Status: ${weatherInfo.weather[0].description}",
                style = MaterialTheme.typography.displayMedium,
            )
        }
    }
}

// Função para formatar o tempo em hora
fun formatTime(timeInMillis: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timeInMillis * 1000)) // Converte para milissegundos
}
