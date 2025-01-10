package pt.umaia.tempofacil.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pt.umaia.tempofacil.R
import pt.umaia.tempofacil.data.PollutionResponse
import pt.umaia.tempofacil.data.PollutionWeatherResponse

@Composable
fun AirPollutionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    airPollutionData: PollutionResponse? = null,
    errorMessage: String? = null,
    isLoading: Boolean = false
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = (Color(0x80FFFFFF)),
            ) {
                // Botões de navegação
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.mipmap.today_foreground),
                            contentDescription = "Agenda",
                            modifier = Modifier.size(50.dp),
                            tint = Color.Unspecified
                        )
                    },
                    selected = false,
                    onClick = { navController.navigate("home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.LightGray
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.mipmap.forecast_foreground),
                            contentDescription = "5 Dias",
                            modifier = Modifier.size(50.dp),
                            tint = Color.Unspecified
                        )
                    },
                    selected = false,
                    onClick = { navController.navigate("fivedays") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.LightGray
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.mipmap.pollution_foreground),
                            contentDescription = "Poluição do Ar",
                            modifier = Modifier.size(50.dp),
                            tint = Color.Unspecified
                        )
                    },
                    selected = false,
                    onClick = { navController.navigate("airpollution") }
                )
            }
        }
    ) { innerPadding ->
        Box {
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Icon(
                        painter = painterResource(id = R.mipmap.ic_air_quality), // Ícone central acima do título
                        contentDescription = "Ícone de Qualidade do Ar",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(
                        "Poluição do Ar",
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    Divider(modifier = Modifier.padding(5.dp),
                        thickness = 3.dp,
                        color = Color.Black)

                    airPollutionData?.let { data ->
                        AirPollutionItem(airPollutionData = data)
                    }
                    errorMessage?.let {
                        Text(text = it, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
        }
    }
}

@Composable
fun AirPollutionItem(
    modifier: Modifier = Modifier,
    airPollutionData: PollutionResponse
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        airPollutionData.list[0].components.apply {
            PollutionDataItem("Monóxido de Carbono (CO):", "$co µg/m³", R.mipmap.ic_co)
            PollutionDataItem("Óxidos de Nitrogênio (NO):", "$no µg/m³", R.mipmap.ic_no)
            PollutionDataItem("Dióxido de Nitrogênio (NO2):", "$no2 µg/m³", R.mipmap.ic_no2)
            PollutionDataItem("Ozônio (O3):", "$o3 µg/m³", R.mipmap.ic_o3)
            PollutionDataItem("Dióxido de Enxofre (SO2):", "$so2 µg/m³", R.mipmap.ic_so2)
            PollutionDataItem("Partículas Finas (PM2.5):", "$pm2_5 µg/m³", R.mipmap.ic_pm2_5)
            PollutionDataItem("Partículas Grossas (PM10):", "$pm10 µg/m³", R.mipmap.ic_pm10)
            PollutionDataItem("Amônia (NH3):", "$nh3 µg/m³", R.mipmap.ic_nh3)
        }
    }
}

@Composable
fun PollutionDataItem(label: String, value: String, iconResId: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified
        )
        Text("$label $value", style = MaterialTheme.typography.titleLarge)
    }
    Divider(modifier = Modifier.padding(5.dp),
        thickness = 1.dp,
        color = Color.Black)
}
