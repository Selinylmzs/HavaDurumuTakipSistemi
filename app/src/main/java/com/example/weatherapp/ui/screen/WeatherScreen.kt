package com.example.weatherapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.ForecastDay
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.ui.viewmodel.WeatherUiState
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Locale

data class WeatherThemeColors(
    val gradientStart: Color,
    val gradientEnd: Color,
    val cardBackground: Color,
    val onCardText: Color,
    val accentColor: Color
)

data class WeatherAlert(
    val title: String,
    val text: String,
    val containerColor: Color,
    val iconColor: Color
)

@Composable
fun getWeatherTheme(code: Int, isDark: Boolean): WeatherThemeColors {
    return when (code) {
        1000 -> { // Clear / Sunny
            if (isDark) {
                WeatherThemeColors(
                    gradientStart = Color(0xFF1E2845),
                    gradientEnd = Color(0xFF0D1B2A),
                    cardBackground = Color(0xFF24305E),
                    onCardText = Color(0xFFE2E2E6),
                    accentColor = Color(0xFFFFB74D)
                )
            } else {
                WeatherThemeColors(
                    gradientStart = Color(0xFFBBDEFB),
                    gradientEnd = Color(0xFFFFE0B2),
                    cardBackground = Color(0xFFFFFFFF),
                    onCardText = Color(0xFF1A1C1E),
                    accentColor = Color(0xFFF57C00)
                )
            }
        }
        1003, 1006, 1009, 1030, 1135, 1147 -> { // Cloudy / Overcast / Mist
            if (isDark) {
                WeatherThemeColors(
                    gradientStart = Color(0xFF263238),
                    gradientEnd = Color(0xFF151D21),
                    cardBackground = Color(0xFF37474F),
                    onCardText = Color(0xFFE2E2E6),
                    accentColor = Color(0xFF90A4AE)
                )
            } else {
                WeatherThemeColors(
                    gradientStart = Color(0xFFECEFF1),
                    gradientEnd = Color(0xFFCFD8DC),
                    cardBackground = Color(0xFFFFFFFF),
                    onCardText = Color(0xFF1A1C1E),
                    accentColor = Color(0xFF546E7A)
                )
            }
        }
        1063, 1072, 1150, 1153, 1180, 1183, 1186, 1189, 1192, 1195, 1240, 1243, 1246 -> { // Rainy
            if (isDark) {
                WeatherThemeColors(
                    gradientStart = Color(0xFF1E293B),
                    gradientEnd = Color(0xFF0F172A),
                    cardBackground = Color(0xFF334155),
                    onCardText = Color(0xFFE2E2E6),
                    accentColor = Color(0xFF64B5F6)
                )
            } else {
                WeatherThemeColors(
                    gradientStart = Color(0xFFD0E1FD),
                    gradientEnd = Color(0xFFB0CBE9),
                    cardBackground = Color(0xFFFFFFFF),
                    onCardText = Color(0xFF1A1C1E),
                    accentColor = Color(0xFF1E88E5)
                )
            }
        }
        1066, 1069, 1114, 1117, 1210, 1213, 1216, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264 -> { // Snowy
            if (isDark) {
                WeatherThemeColors(
                    gradientStart = Color(0xFF1B3150),
                    gradientEnd = Color(0xFF0B1B2F),
                    cardBackground = Color(0xFF2C4A6F),
                    onCardText = Color(0xFFE2E2E6),
                    accentColor = Color(0xFF81D4FA)
                )
            } else {
                WeatherThemeColors(
                    gradientStart = Color(0xFFE0F7FA),
                    gradientEnd = Color(0xFFB2EBF2),
                    cardBackground = Color(0xFFFFFFFF),
                    onCardText = Color(0xFF1A1C1E),
                    accentColor = Color(0xFF00ACC1)
                )
            }
        }
        1087, 1273, 1276, 1279, 1282 -> { // Thunderstorm
            if (isDark) {
                WeatherThemeColors(
                    gradientStart = Color(0xFF1A0033),
                    gradientEnd = Color(0xFF0F0022),
                    cardBackground = Color(0xFF2D124D),
                    onCardText = Color(0xFFE2E2E6),
                    accentColor = Color(0xFFE040FB)
                )
            } else {
                WeatherThemeColors(
                    gradientStart = Color(0xFFD1C4E9),
                    gradientEnd = Color(0xFFB39DDB),
                    cardBackground = Color(0xFFFFFFFF),
                    onCardText = Color(0xFF1A1C1E),
                    accentColor = Color(0xFF7B1FA2)
                )
            }
        }
        else -> { // Default Blue Theme
            if (isDark) {
                WeatherThemeColors(
                    gradientStart = Color(0xFF0B121F),
                    gradientEnd = Color(0xFF1A2333),
                    cardBackground = Color(0xFF1E293B),
                    onCardText = Color(0xFFE2E2E6),
                    accentColor = Color(0xFF4FC3F7)
                )
            } else {
                WeatherThemeColors(
                    gradientStart = Color(0xFFF5F7FA),
                    gradientEnd = Color(0xFFE3F2FD),
                    cardBackground = Color(0xFFFFFFFF),
                    onCardText = Color(0xFF1A1C1E),
                    accentColor = Color(0xFF1E88E5)
                )
            }
        }
    }
}

fun getDayName(dateStr: String?): String {
    if (dateStr == null) return ""
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateStr)
        val outputFormat = SimpleDateFormat("EEEE", Locale("tr"))
        outputFormat.format(date ?: "")
    } catch (e: Exception) {
        dateStr
    }
}

fun formatHour(timeStr: String?): String {
    if (timeStr == null) return ""
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = inputFormat.parse(timeStr)
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        outputFormat.format(date ?: "")
    } catch (e: Exception) {
        timeStr
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = viewModel(),
    useDarkTheme: Boolean = isSystemInDarkTheme()
) {
    var cityInput by remember { mutableStateOf("Istanbul") }
    val uiState by viewModel.uiState.collectAsState()
    val favorites by viewModel.favoriteCities.collectAsState()
    val suggestions by viewModel.searchSuggestions.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchLocationAndWeather()
    }

    val conditionCode = when (val state = uiState) {
        is WeatherUiState.Success -> state.weather.current?.condition?.code ?: 0
        else -> 0
    }
    val themeColors = getWeatherTheme(conditionCode, useDarkTheme)

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            themeColors.gradientStart,
            themeColors.gradientEnd
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            
            // Header Row with Title and Theme Switcher Button using Emojis
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hava Durumu",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (useDarkTheme) Color.White else MaterialTheme.colorScheme.primary
                )

                IconButton(onClick = {
                    val nextMode = if (useDarkTheme) "light" else "dark"
                    viewModel.setThemeMode(nextMode)
                }) {
                    Text(
                        text = if (useDarkTheme) "☀️" else "🌙",
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            OutlinedTextField(
                value = cityInput,
                onValueChange = { 
                    cityInput = it
                    viewModel.searchCities(it)
                },
                label = { Text("Şehir Adı") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = themeColors.accentColor,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Autocomplete Dropdown List
            if (suggestions.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = themeColors.cardBackground
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        suggestions.take(5).forEach { suggestion ->
                            val name = suggestion.name ?: ""
                            val region = suggestion.region ?: ""
                            val country = suggestion.country ?: ""
                            val displayName = listOfNotNull(
                                name,
                                region.takeIf { it.isNotEmpty() },
                                country.takeIf { it.isNotEmpty() }
                            ).joinToString(", ")

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        cityInput = name
                                        val queryId = suggestion.id?.let { "id:$it" } ?: "$name, $country"
                                        viewModel.getWeather(queryId)
                                        viewModel.clearSuggestions()
                                    }
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Location",
                                    tint = themeColors.onCardText.copy(alpha = 0.6f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = displayName,
                                    color = themeColors.onCardText,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Favorite Cities Quick Access Chips
            if (favorites.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    favorites.forEach { favCity ->
                        InputChip(
                            selected = false,
                            onClick = {
                                cityInput = favCity
                                viewModel.getWeather(favCity)
                                viewModel.clearSuggestions()
                            },
                            label = { Text(favCity) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Kaldır",
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clickable { viewModel.toggleFavorite(favCity) }
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Favori",
                                    tint = Color(0xFFFBC02D),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = { 
                    viewModel.getWeather(cityInput)
                    viewModel.clearSuggestions()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = themeColors.accentColor
                )
            ) {
                Text(
                    text = "Sorgula",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                when (val state = uiState) {
                    is WeatherUiState.Idle -> {
                        Text(
                            text = "Hava durumunu öğrenmek istediğiniz şehri yazıp arayın.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 32.dp),
                            color = themeColors.onCardText
                        )
                    }
                    is WeatherUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = themeColors.accentColor,
                                strokeWidth = 4.dp
                            )
                        }
                    }
                    is WeatherUiState.Success -> {
                        Column {
                            WeatherCard(weather = state.weather, viewModel = viewModel, themeColors = themeColors)
                            
                            SmartAlertCard(weather = state.weather, themeColors = themeColors, useDarkTheme = useDarkTheme)

                            state.weather.forecast?.forecastday?.firstOrNull()?.hour?.let { hourList ->
                                HourlyForecastBar(hourlyList = hourList, themeColors = themeColors)
                            }

                            state.weather.forecast?.let {
                                ForecastCard(forecast = it, themeColors = themeColors)
                            }
                        }
                    }
                    is WeatherUiState.Error -> {
                        ErrorCard(message = state.message)
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherResponse, viewModel: WeatherViewModel, themeColors: WeatherThemeColors) {
    val temp = weather.current?.tempC?.toInt() ?: 0
    val feelsLike = weather.current?.feelsLikeC?.toInt() ?: 0
    val humidity = weather.current?.humidity ?: 0
    val windSpeed = weather.current?.windKph ?: 0.0
    val desc = weather.current?.condition?.text ?: ""
    val rawIcon = weather.current?.condition?.icon ?: ""
    val iconUrl = if (rawIcon.startsWith("//")) "https:$rawIcon" else rawIcon

    val cityName = weather.location?.name ?: ""
    val country = weather.location?.country ?: ""
    val displayName = if (country.isNotEmpty()) "$cityName, $country" else cityName

    val isFav = viewModel.isFavorite(cityName)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.cardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.size(48.dp))
                
                Text(
                    text = displayName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = themeColors.onCardText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { viewModel.toggleFavorite(cityName) }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Favoriye Ekle",
                        tint = if (isFav) Color(0xFFFBC02D) else themeColors.onCardText.copy(alpha = 0.2f),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (iconUrl.isNotEmpty()) {
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = desc,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = "$temp°C",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Black
                    ),
                    color = themeColors.onCardText
                )
            }

            Text(
                text = desc,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = themeColors.accentColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(themeColors.onCardText.copy(alpha = 0.15f))
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    label = "Hissedilen",
                    value = "$feelsLike°C",
                    textColor = themeColors.onCardText
                )
                WeatherDetailItem(
                    label = "Nem",
                    value = "%$humidity",
                    textColor = themeColors.onCardText
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    label = "Rüzgar",
                    value = "$windSpeed km/sa",
                    textColor = themeColors.onCardText
                )
                WeatherDetailItem(
                    label = "Basınç",
                    value = "${weather.current?.pressureMb?.toInt() ?: 0} hPa",
                    textColor = themeColors.onCardText
                )
            }
        }
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String, textColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(110.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = textColor.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            color = textColor
        )
    }
}

@Composable
fun SmartAlertCard(weather: WeatherResponse, themeColors: WeatherThemeColors, useDarkTheme: Boolean) {
    val conditionCode = weather.current?.condition?.code ?: 0
    val uv = weather.current?.uv ?: 0.0
    val windSpeed = weather.current?.windKph ?: 0.0
    val desc = weather.current?.condition?.text ?: ""

    val alert = when {
        conditionCode in listOf(1087, 1273, 1276, 1279, 1282) -> {
            WeatherAlert(
                "Gökgürültülü Fırtına",
                "Fırtına ve yıldırım riski bulunuyor! Güvenli bir kapalı alanda kalmaya özen gösterin.",
                Color(0xFFFDE8E8),
                Color(0xFFE53935)
            )
        }
        conditionCode in listOf(1066, 1069, 1114, 1117, 1210, 1213, 1216, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264) -> {
            WeatherAlert(
                "Karlı / Buzlanma",
                "Kar yağışı ve don riski var! Kalın giyinmeyi, atkı ve eldivenlerinizi yanınıza almayı unutmayın.",
                Color(0xFFE0F7FA),
                Color(0xFF00ACC1)
            )
        }
        conditionCode in listOf(1063, 1072, 1150, 1153, 1180, 1183, 1186, 1189, 1192, 1195, 1240, 1243, 1246) -> {
            WeatherAlert(
                "Yağmurlu",
                "Yağış bekleniyor veya yağıyor! Dışarı çıkarken şemsiyenizi almayı unutmayın.",
                Color(0xFFE3F2FD),
                Color(0xFF1E88E5)
            )
        }
        uv >= 6.0 -> {
            WeatherAlert(
                "Yüksek UV",
                "Yüksek UV İndeksi ($uv)! Güneş kremi sürün, şapka takın ve güneş gözlüğünüzü unutmayın.",
                Color(0xFFFFF3E0),
                Color(0xFFF57C00)
            )
        }
        windSpeed > 25.0 -> {
            WeatherAlert(
                "Rüzgarlı",
                "Rüzgar hızı yüksek ($windSpeed km/sa)! Şapkanızın uçmamasına dikkat edin ve rüzgardan korunun.",
                Color(0xFFF5F5F5),
                Color(0xFF757575)
            )
        }
        uv in 3.0..5.9 -> {
            WeatherAlert(
                "Orta UV",
                "Orta düzeyde UV İndeksi ($uv). Uzun süre güneş altında kalacaksanız güneş gözlüğü takabilirsiniz.",
                Color(0xFFFFFDE7),
                Color(0xFFFBC02D)
            )
        }
        conditionCode in listOf(1003, 1006, 1009, 1030, 1135, 1147) -> {
            WeatherAlert(
                desc,
                "Hava bulutlu ve kapalı. Rahat ve serin bir yürüyüş yapmak için ideal bir gün!",
                Color(0xFFECEFF1),
                Color(0xFF546E7A)
            )
        }
        else -> {
            WeatherAlert(
                "Açık / Güzel Hava",
                "Hava açık ve güzel! Dışarıda yürüyüş yapmak veya temiz hava almak için harika bir gün.",
                Color(0xFFE8F5E9),
                Color(0xFF2E7D32)
            )
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (useDarkTheme) themeColors.cardBackground else alert.containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Bilgi",
                tint = alert.iconColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "$desc - Öneri",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (useDarkTheme) themeColors.onCardText else Color(0xFF1A1C1E)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = alert.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (useDarkTheme) themeColors.onCardText.copy(alpha = 0.8f) else Color(0xFF313131)
                )
            }
        }
    }
}

@Composable
fun HourlyForecastBar(hourlyList: List<Hour>, themeColors: WeatherThemeColors) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.cardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Bugün Saatlik Durum",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = themeColors.onCardText,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                hourlyList.forEach { hourItem ->
                    HourlyItem(hourItem = hourItem, themeColors = themeColors)
                }
            }
        }
    }
}

@Composable
fun HourlyItem(hourItem: Hour, themeColors: WeatherThemeColors) {
    val formattedTime = formatHour(hourItem.time)
    val temp = hourItem.tempC?.toInt() ?: 0
    val desc = hourItem.condition?.text ?: ""
    val rawIcon = hourItem.condition?.icon ?: ""
    val iconUrl = if (rawIcon.startsWith("//")) "https:$rawIcon" else rawIcon

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            text = formattedTime,
            style = MaterialTheme.typography.labelSmall,
            color = themeColors.onCardText.copy(alpha = 0.6f)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        if (iconUrl.isNotEmpty()) {
            AsyncImage(
                model = iconUrl,
                contentDescription = desc,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$temp°C",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = themeColors.onCardText
        )
    }
}

@Composable
fun ForecastCard(forecast: Forecast, themeColors: WeatherThemeColors) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.cardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "7 Günlük Tahmin",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = themeColors.onCardText,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            forecast.forecastday?.forEachIndexed { index, forecastDay ->
                ForecastRow(forecastDay = forecastDay, themeColors = themeColors)
                if (index < (forecast.forecastday.size - 1)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(themeColors.onCardText.copy(alpha = 0.08f))
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ForecastRow(forecastDay: ForecastDay, themeColors: WeatherThemeColors) {
    val dayName = getDayName(forecastDay.date)
    val maxTemp = forecastDay.day?.maxTempC?.toInt() ?: 0
    val minTemp = forecastDay.day?.minTempC?.toInt() ?: 0
    val desc = forecastDay.day?.condition?.text ?: ""
    val rawIcon = forecastDay.day?.condition?.icon ?: ""
    val iconUrl = if (rawIcon.startsWith("//")) "https:$rawIcon" else rawIcon

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.width(110.dp)) {
            Text(
                text = dayName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = themeColors.onCardText
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.labelSmall,
                color = themeColors.onCardText.copy(alpha = 0.6f)
            )
        }

        if (iconUrl.isNotEmpty()) {
            AsyncImage(
                model = iconUrl,
                contentDescription = desc,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = "$maxTemp°C / $minTemp°C",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = themeColors.onCardText,
            textAlign = TextAlign.End,
            modifier = Modifier.width(90.dp)
        )
    }
}

@Composable
fun ErrorCard(message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Hata",
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
