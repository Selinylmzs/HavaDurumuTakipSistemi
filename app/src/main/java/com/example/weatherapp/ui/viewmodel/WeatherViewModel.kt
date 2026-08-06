package com.example.weatherapp.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.api.RetrofitClient
import com.example.weatherapp.data.model.SearchSuggestion
import com.example.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface WeatherUiState {
    object Idle : WeatherUiState
    object Loading : WeatherUiState
    data class Success(val weather: WeatherResponse) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _favoriteCities = MutableStateFlow<Set<String>>(emptySet())
    val favoriteCities: StateFlow<Set<String>> = _favoriteCities.asStateFlow()

    private val _themeMode = MutableStateFlow("system")
    val themeMode: StateFlow<String> = _themeMode.asStateFlow()

    private val _searchSuggestions = MutableStateFlow<List<SearchSuggestion>>(emptyList())
    val searchSuggestions: StateFlow<List<SearchSuggestion>> = _searchSuggestions.asStateFlow()

    init {
        loadFavorites()
        loadThemeMode()
    }

    private fun loadThemeMode() {
        _themeMode.value = sharedPrefs.getString("theme_mode", "system") ?: "system"
    }

    fun setThemeMode(mode: String) {
        if (mode in listOf("system", "light", "dark")) {
            sharedPrefs.edit().putString("theme_mode", mode).apply()
            _themeMode.value = mode
        }
    }

    private fun loadFavorites() {
        val savedFavorites = sharedPrefs.getStringSet("favorites", emptySet()) ?: emptySet()
        _favoriteCities.value = savedFavorites.toSortedSet()
    }

    fun toggleFavorite(city: String) {
        if (city.isBlank()) return
        val normalizedCity = city.trim().lowercase().replaceFirstChar { it.uppercase() }
        val currentFavorites = _favoriteCities.value.toMutableSet()
        
        if (currentFavorites.contains(normalizedCity)) {
            currentFavorites.remove(normalizedCity)
        } else {
            currentFavorites.add(normalizedCity)
        }
        
        sharedPrefs.edit().putStringSet("favorites", currentFavorites).apply()
        _favoriteCities.value = currentFavorites.toSortedSet()
    }

    fun isFavorite(city: String): Boolean {
        if (city.isBlank()) return false
        val normalizedCity = city.trim().lowercase().replaceFirstChar { it.uppercase() }
        return _favoriteCities.value.contains(normalizedCity)
    }

    fun getWeather(city: String) {
        if (city.isBlank()) {
            _uiState.value = WeatherUiState.Error("Lütfen bir şehir adı girin")
            return
        }

        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val apiKey = BuildConfig.WEATHER_API_KEY
                
                if (apiKey.isBlank()) {
                    _uiState.value = WeatherUiState.Error("API Key tanımlı değil!")
                    return@launch
                }

                val response = RetrofitClient.apiService.getCurrentWeather(
                    city = city,
                    apiKey = apiKey
                )
                
                if (response.location != null) {
                    val resolvedCity = response.location.name ?: city
                    sharedPrefs.edit().putString("last_city", resolvedCity).apply()
                    _uiState.value = WeatherUiState.Success(response)
                } else {
                    _uiState.value = WeatherUiState.Error("Şehir bulunamadı!")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = WeatherUiState.Error(
                    e.localizedMessage ?: "Bağlantı hatası oluştu veya geçersiz şehir!"
                )
            }
        }
    }

    fun searchCities(query: String) {
        if (query.trim().length < 3) {
            _searchSuggestions.value = emptyList()
            return
        }
        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.WEATHER_API_KEY
                if (apiKey.isNotBlank()) {
                    val results = RetrofitClient.apiService.searchCities(query.trim(), apiKey)
                    _searchSuggestions.value = results
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clearSuggestions() {
        _searchSuggestions.value = emptyList()
    }

    fun fetchLocationAndWeather() {
        val context = getApplication<Application>().applicationContext
        val locationStr = getLastKnownLocation(context)
        if (locationStr != null) {
            getWeather(locationStr)
        } else {
            getWeather("Istanbul")
        }
    }

    private fun getLastKnownLocation(context: Context): String? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null
        }
        val providers = locationManager.getProviders(true)
        var bestLocation: android.location.Location? = null
        for (provider in providers) {
            val l = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                bestLocation = l
            }
        }
        return bestLocation?.let { "${it.latitude},${it.longitude}" }
    }
}
