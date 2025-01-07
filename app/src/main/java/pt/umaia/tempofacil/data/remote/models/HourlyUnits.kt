package pt.umaia.tempofacil.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyUnits(
    @SerialName("temperature_2m")
    val temperature2m: String,
    @SerialName("time")
    val time: String
)