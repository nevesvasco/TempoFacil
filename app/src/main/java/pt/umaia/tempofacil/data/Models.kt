package pt.umaia.tempofacil.data

import com.squareup.moshi.Json


data class WeatherResponse(
    @Json(name = "current") val current: CurrentWeatherResponse,
    @Json(name = "daily") val daily: List<DailyWeatherData>,
    @Json(name = "hourly") val hourly: List<HourlyWeatherData>
)

data class FiveResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<FiveWeatherResponse>
)

data class PollutionWeatherResponse(
    val main: PollutionMain,
    val components: Components,
    val dt: Long,
)

data class PollutionResponse(
    val coord: Coords,
    val list: List<PollutionWeatherResponse>
)

data class Coords(
    val lon: Double,
    val lat: Double
)

data class CurrentWeatherResponse(
    val main: Main,
    val weather: List<WeatherDescription>,
    val wind: Wind,
    val name: String
)

data class FiveWeatherResponse(
    val dt: Long,
    val main: FiveMain,
    val weather: List<WeatherDescription>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys,
    val dt_txt: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class FiveMain(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Double
)

data class PollutionMain(
    val aqi: Int
)

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class PollutionWeatherDescription(
    val id: Int, // O identificador da condição do tempo
    val main: String, // Condição geral do tempo (ex: "Clear", "Clouds", etc.)
    val description: String, // Descrição detalhada do tempo (ex: "clear sky", "few clouds")
    val icon: String // Ícone do tempo (ex: "01d", "02n")
)

data class Clouds(
    val all: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class AirQuality(
    val components: Components, // Componentes do ar
    val dt: Long // Timestamp de quando as informações foram coletadas
)

data class Rain(
    val `3h`: Double?  // Rain volume for the last 3 hours
)

data class Components(
    val co: Double, // Monóxido de carbono
    val no: Double, // Nitrogênio (óxido)
    val no2: Double, // Dióxido de nitrogênio
    val o3: Double, // Ozone
    val so2: Double, // Dióxido de enxofre
    val pm2_5: Double, // Partículas finas
    val pm10: Double, // Partículas grossas
    val nh3: Double // Amônia
)

// Classe que descreve informações do sistema, como a parte do dia (pod)
data class Sys(
    val pod: String
)

data class DailyWeatherData(
    @Json(name = "dt") val time: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
    @Json(name = "uvi") val uvIndex: Double,
    @Json(name = "weather") val weather: List<WeatherDescription>
)

data class HourlyWeatherData(
    @Json(name = "dt") val time: Long,
    @Json(name = "temp") val temperature: Double,
    @Json(name = "weather") val weather: List<WeatherDescription>
)

