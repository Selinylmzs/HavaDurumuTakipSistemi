# 🌤️ Android Hava Durumu ve Takip Sistemi

[![Platform](https://img.shields.io/badge/Platform-Android%20%28Kotlin%29-blue.svg?logo=android)](https://developer.android.com)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2F%20StateFlow-orange.svg)]()
[![UI](https://img.shields.io/badge/UI-Jetpack%20Compose%20%2F%20Material%20Design%203-blue.svg?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

---

## 📋 Proje Bilgileri

*   **Proje Yürütücüsü:** Selin Yılmaz
*   **Bölüm:** Bilgisayar Mühendisliği Öğrencisi
*   **Proje Adı:** Android Hava Durumu ve Takip Sistemi
*   **Platform:** Android (Kotlin)
*   **Arayüz Mimarisi:** Jetpack Compose & Material Design 3

---

## 🎯 Proje Özeti

Bu proje; harici bir hava durumu API'si üzerinden anlık sıcaklık, nem, basınç, rüzgar hızı ve UV indeksi gibi atmosferik verileri çekmek, kullanıcının GPS konumuna göre dinamik hava durumu sunmak ve akıllı bildirimlerle kullanıcıyı yönlendirmek için geliştirilmiş modern bir Android uygulamasıdır.

---

## ⚙️ Uygulama Ne Yapar? (Akış ve Özellikler)

*   **📍 Konum Tespiti:** Cihazın anlık GPS koordinatlarını alarak otomatik olarak yerel hava durumunu getirir. GPS kapalıyken veya konum izni verilmediğinde, uygulamanın stabil çalışabilmesi amacıyla varsayılan olarak **İstanbul**'u yükler (Fallback mechanism).
*   **🔔 Akıllı Öneri ve Bildirim Sistemi:** Hava koşullarına göre (Güneşli, Yağmurlu vb.) kullanıcıya özel tavsiyeler verir (Örneğin: yüksek UV indeksinde güneş gözlüğü takılması, yağmurlu havada şemsiye alınması gibi) ve `WorkManager` aracılığıyla arka planda periyodik olarak bildirimler tetikler.
*   **📊 Saatlik ve Günlük Tahmin:** Önümüzdeki saatlerin sıcaklık değişimlerini yatay kaydırılabilir şık bir çubukta gösterir ve 7 günlük detaylı tahmin raporlarını listeler.
*   **🎨 Dinamik Tema:** Hava durumunun türüne (Güneşli, Yağmurlu, Karlı, Fırtınalı) ve kullanıcı tercihine göre otomatik uyum sağlayan, durum çubuğuyla senkronize **Aydınlık/Karanlık Mod** desteği sunar.

---

## 🛠️ Teknolojiler

*   **Dil:** Kotlin
*   **Arayüz (UI):** Jetpack Compose, Material Design 3
*   **Veri Altyapısı:** REST API entegrasyonu (WeatherAPI, Retrofit & OkHttp)
*   **Arka Plan İşlemleri:** Jetpack WorkManager (Arka planda periyodik veri analizi ve yerel bildirim tetikleme)
*   **Mimari:** MVVM (Model-View-ViewModel), Kotlin Coroutines & StateFlow (Reaktif durum yönetimi)
*   **Yerel Hafıza:** SharedPreferences (Tema seçimi ve favori şehirlerin diske kaydedilmesi)
*   **IDE:** Android Studio

---

## 📸 Görsel Yolculuk

Uygulamanın arayüz tasarımı, dinamik renk şemaları ve akıllı öneri kartları aşağıdaki ekran görüntülerinde detaylandırılmıştır:

### 1. İstanbul - Güneşli Arayüzü (Karanlık Tema)
Güneşli hava koşullarında koyu mavi tonlarına bürünen arayüz. Ana kart üzerinde anlık sıcaklık, hissedilen sıcaklık, rüzgar hızı, nem ve basınç değerleri listelenmektedir. Akıllı öneri kartında güneşli havaya uygun Türkçe yürüyüş tavsiyesi yer almaktadır.

<p align="center">
  <img src="screenshots/gorsel1.jpg" width="300" alt="Karanlık Tema - İstanbul Güneşli Görünümü"/>
</p>

### 2. Saatlik ve Haftalık Detaylar (Karanlık Tema)
Aynı ekranın alt bölümünde; yatayda kaydırılabilir **Bugün Saatlik Durum** çubuğu (gece saatlerine ait durum ikonları ve sıcaklıklar) ile dinamik durum ikonlarına sahip **7 Günlük Tahmin** listesi gösterilmektedir.

<p align="center">
  <img src="screenshots/gorsel2.jpg" width="300" alt="Karanlık Tema - Tahmin Kartları"/>
</p>

### 3. Ankara - Parçalı Bulutlu Arayüzü (Karanlık Tema)
Parçalı bulutlu hava koşulunda arayüz renk şemasının gri-mavi tonlara bürünmesi. Akıllı öneri kartında bulutlu hava koşullarına uygun, serin havada yürüyüş yapmayı tavsiye eden öneri dinamik olarak güncellenmiştir.

<p align="center">
  <img src="screenshots/gorsel3.jpg" width="300" alt="Karanlık Tema - Ankara Parçalı Bulutlu"/>
</p>

### 4. Saatlik ve Haftalık Detaylar - Ankara (Karanlık Tema)
Ankara araması sonrasındaki saatlik sıcaklık akışı ve haftalık tahmin verilerinin kullanıcıya sunumu.

<p align="center">
  <img src="screenshots/gorsel4.jpg" width="300" alt="Karanlık Tema - Ankara Tahmin Raporları"/>
</p>

### 5. Paris - Güneşli Arayüzü (Aydınlık Tema)
Kullanıcının üst paneldeki simgeye tıklayarak **Aydınlık Moda** geçmesiyle birlikte, güneşli havaya özel açık mavi ve sarı gradyan geçişine sahip dinamik tema görünümü. UV indeksinin (3.4) seviyesine göre güneş gözlüğü takılması önerisi sunulmaktadır.

<p align="center">
  <img src="screenshots/gorsel5.jpg" width="300" alt="Aydınlık Tema - Paris Görünümü"/>
</p>

---

## 📈 Beklenen Sonuç

Uygulama başarıyla çalıştırıldığında:
*   Anlık GPS konumuna veya arama çubuğundan girilen şehre ait güncel hava durumu verileri ekrana yansır.
*   Saatlik ve 7 günlük tahmin kartları akıcı bir şekilde listelenir.
*   Hava durumuna uygun akıllı öneri kartları dinamik olarak güncellenir ve arka plan servisleri ile bildirimler aktif hale gelir.

---

## 📄 Lisans

Bu proje **[MIT Lisansı](LICENSE)** altında lisanslanmıştır. Detaylı bilgi için lisans dosyasını inceleyebilirsiniz.

---

<p align="center">
  <b>Geliştiren:</b> Selin Yılmaz <br>
  <i>Bilgisayar Mühendisliği Öğrencisi</i>
</p>
