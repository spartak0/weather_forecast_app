package com.example.weather.domain;

import androidx.annotation.HalfFloat;

import com.example.weather.domain.model.forecast.WeatherData;

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

    Observable<Pair<Float,String>> getCurrentWeatherDataByCoord(String lat, String lon, String units);
    Observable<Pair<Float,String>> getDailyWeatherDataByCoord(String lat, String lon, int day , String units);
    Observable<ArrayList<Triple<String,String,String>>> getHourlyWeatherByCoord(String lat, String lon, String units);

}
