package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.SearchSuggestion
import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String,
        @Query("days") days: Int = 7,
        @Query("lang") lang: String = "tr"
    ): WeatherResponse

    @GET("search.json")
    suspend fun searchCities(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): List<SearchSuggestion>
}
