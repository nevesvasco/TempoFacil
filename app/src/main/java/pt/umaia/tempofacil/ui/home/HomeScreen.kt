package pt.umaia.tempofacil.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import pt.umaia.tempofacil.data.CurrentWeatherResponse
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
    currentWeather: CurrentWeatherResponse? = null, // Passando os dados de previsão atual diretamente
    dailyWeatherInfo: List<DailyWeatherData> = emptyList(), // Passando os dados diários
    errorMessage: String? = null, // Passando a mensagem de erro diretamente
    isLoading: Boolean = false  // Indicando o estado de carregamento
) {
    Scaffold(
        bottomBar = (

                {
                    NavigationBar(
                        containerColor = Color(0xFFA1B8CC), // Azul inspirado nos azulejos portugueses
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
                                    painter = painterResource(id = R.mipmap.sixteen_foreground),
                                    contentDescription = "Dezasseis Dias",
                                    modifier = Modifier.size(50.dp), // Tamanho do ícone ajustado
                                    tint = Color.Unspecified // Desativa a tintagem para preservar a cor original da imagem
                                )
                            },
                            selected = false, // Atualize a lógica para seleção dinâmica
                            onClick = { navController.navigate("dezasseisdias") },
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
                                    painter = painterResource(id = R.mipmap.mes_foreground),
                                    contentDescription = "30 Dias",
                                    modifier = Modifier.size(50.dp), // Tamanho do ícone ajustado
                                    tint = Color.Unspecified // Desativa a tintagem para preservar a cor original da imagem
                                )
                            },
                            selected = false, // Atualize a lógica para seleção dinâmica
                            onClick = { navController.navigate("mensal") }
                        )
                    }
                }
                )
    ) { innerPadding ->
        Box{
            Image(
                painter = painterResource(id = R.mipmap.wallpaper_foreground),
                contentDescription = "Fundo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
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

                    // Se houver erro, exibe a mensagem
                    errorMessage?.let {
                        Text(text = it, color = androidx.compose.ui.graphics.Color.Red)
                    }
                }
            }
        }
    }
}


@Composable
fun CurrentWeatherItem(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeatherResponse
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${currentWeather.weather[0].icon}@2x.png"),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = "${currentWeather.main.temp}$degreeTxt",
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = currentWeather.weather[0].description,
                style = MaterialTheme.typography.displaySmall
            )
            Divider(modifier = Modifier.padding(5.dp),
                thickness = 3.dp,
                color = Color.Black)
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn, // Ícone de localização
                    contentDescription = "Localização",
                    modifier = Modifier
                        .size(48.dp),
                    tint = Color.Red
                )
                Text(
                    text = currentWeather.name,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                )
            }
            Divider(modifier = Modifier.padding(5.dp),
                thickness = 1.dp,
                color = Color.Black)
            Text(
                text = "Sensação de Temperatura: " + currentWeather.main.feels_like + "ºC",
                style = MaterialTheme.typography.titleLarge,
            )
            Divider(modifier = Modifier.padding(5.dp),
                thickness = 1.dp,
                color = Color.Black)
            Text(
                text = "Temperatura Mínima: " + currentWeather.main.temp_min + "ºC",
                style = MaterialTheme.typography.titleLarge,
            )
            Divider(modifier = Modifier.padding(5.dp),
                thickness = 1.dp,
                color = Color.Black)
            Text(
                text = "Temperatura Máxima: " + currentWeather.main.temp_max + "ºC",
                style = MaterialTheme.typography.titleLarge,
            )
            Divider(modifier = Modifier.padding(5.dp),
                thickness = 1.dp,
                color = Color.Black)
            Text(
                text = "Pressão: " + currentWeather.main.pressure,
                style = MaterialTheme.typography.titleLarge,
            )
            Divider(modifier = Modifier.padding(5.dp),
                thickness = 1.dp,
                color = Color.Black)
            Text(
                text = "Humidade: " + currentWeather.main.humidity,
                style = MaterialTheme.typography.titleLarge,
            )
            Divider(modifier = Modifier.padding(5.dp),
                thickness = 1.dp,
                color = Color.Black)
            Text(
                text = "Velocidade do Vento: " + currentWeather.wind.speed + " KM/h",
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

// Função para formatar o tempo em hora
fun formatTime(timeInMillis: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timeInMillis * 1000)) // Converte para milissegundos
}
