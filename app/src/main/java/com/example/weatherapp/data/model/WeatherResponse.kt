package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: Location?,
    @SerializedName("current") val current: Current?,
    @SerializedName("forecast") val forecast: Forecast?
)

data class Location(
    @SerializedName("name") val name: String?,
    @SerializedName("region") val region: String?,
    @SerializedName("country") val country: String?
)

data class Current(
    @SerializedName("temp_c") val tempC: Double?,
    @SerializedName("feelslike_c") val feelsLikeC: Double?,
    @SerializedName("condition") val condition: Condition?,
    @SerializedName("wind_kph") val windKph: Double?,
    @SerializedName("pressure_mb") val pressureMb: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("uv") val uv: Double?
)

data class Condition(
    @SerializedName("text") val text: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("code") val code: Int?
)

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>?
)

data class ForecastDay(
    @SerializedName("date") val date: String?,
    @SerializedName("date_epoch") val dateEpoch: Long?,
    @SerializedName("day") val day: Day?,
    @SerializedName("hour") val hour: List<Hour>?
)

data class Day(
    @SerializedName("maxtemp_c") val maxTempC: Double?,
    @SerializedName("mintemp_c") val minTempC: Double?,
    @SerializedName("avgtemp_c") val avgTempC: Double?,
    @SerializedName("condition") val condition: Condition?
)

data class Hour(
    @SerializedName("time") val time: String?,
    @SerializedName("time_epoch") val timeEpoch: Long?,
    @SerializedName("temp_c") val tempC: Double?,
    @SerializedName("condition") val condition: Condition?,
    @SerializedName("is_day") val isDay: Int?
)

data class SearchSuggestion(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("region") val region: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?
)
