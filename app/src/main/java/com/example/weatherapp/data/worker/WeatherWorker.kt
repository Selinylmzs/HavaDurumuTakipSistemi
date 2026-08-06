package com.example.weatherapp.data.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.api.RetrofitClient
import com.example.weatherapp.ui.notification.NotificationHelper

class WeatherWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val apiKey = BuildConfig.WEATHER_API_KEY
        if (apiKey.isBlank()) {
            return Result.failure()
        }

        val locationStr = getLastKnownLocation(applicationContext)
        val query = locationStr ?: "Istanbul"

        return try {
            val response = RetrofitClient.apiService.getCurrentWeather(
                city = query,
                apiKey = apiKey
            )

            val cityName = response.location?.name ?: "Istanbul"
            val conditionCode = response.current?.condition?.code ?: 0
            val uv = response.current?.uv ?: 0.0
            val windSpeed = response.current?.windKph ?: 0.0
            val desc = response.current?.condition?.text ?: ""
            val temp = response.current?.tempC?.toInt() ?: 0

            val title = "$cityName'da Hava: $desc ($temp°C)"
            
            val alertMessage = when {
                conditionCode in listOf(1087, 1273, 1276, 1279, 1282) -> {
                    "Fırtına riski var! Güvenli kapalı alanlarda kalmaya özen gösterin."
                }
                conditionCode in listOf(1066, 1069, 1114, 1117, 1210, 1213, 1216, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264) -> {
                    "Kar yağışı ve don riski var! Kalın giyinin, atkı ve eldivenlerinizi unutmayın."
                }
                conditionCode in listOf(1063, 1072, 1150, 1153, 1180, 1183, 1186, 1189, 1192, 1195, 1240, 1243, 1246) -> {
                    "Yağmur yağıyor veya bekleniyor! Şemsiyenizi almayı unutmayın."
                }
                uv >= 6.0 -> {
                    "Yüksek UV İndeksi ($uv)! Güneş kremi sürmeyi ve güneş gözlüğü takmayı unutmayın."
                }
                windSpeed > 25.0 -> {
                    "Rüzgar hızı yüksek ($windSpeed km/sa)! Şapkanızın uçmamasına dikkat edin."
                }
                else -> {
                    "Hava güzel ve sakin. Yürüyüşe çıkmak için harika bir gün!"
                }
            }

            val notificationHelper = NotificationHelper(applicationContext)
            notificationHelper.showNotification(title, alertMessage)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
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
