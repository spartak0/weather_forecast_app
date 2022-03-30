package com.example.weather.domain;

import android.content.Context;

import androidx.annotation.HalfFloat;

import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.utils.SettingManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import kotlin.Pair;
import kotlin.Triple;

public interface Repository {

    Observable<List<WeatherData>> getAllWeather();

    Observable<List<WeatherData>> getFavoriteWeather();

    Observable<WeatherData> getWeatherById(int id);

    Completable addWeather(WeatherData weatherData);

    Completable deleteWeather(WeatherData weatherData);

    Completable deleteWeatherById(int id);

    Completable updateWeather(WeatherData weatherData);

    Observable<Pair<Float,String>> getCurrentWeatherDataByCoord(String lat, String lon);

    Observable<Pair<Float,String>> getDailyWeatherDataByCoord(String lat, String lon, int day);

    Observable<ArrayList<Triple<String,String,String>>> getHourlyWeatherByCoord(String lat, String lon);

    Observable<String> getTimezone(String lat, String lon);

    void loadLocale(Context context);

    void setLocale(Context context, String lang);

    Boolean isNetworkAvailable(Context context);

}
