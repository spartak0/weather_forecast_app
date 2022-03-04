package com.example.weather.domain;

import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface Repository {

    Observable<List<WeatherData>> getAllWeather();

    WeatherData getWeatherById(int id);

    Completable addWeather(WeatherData weatherData);

    Completable deleteWeather(WeatherData weatherData);

    Completable updateWeather(WeatherData weatherData);

    Observable<Float> getCurrentWeatherDataByCoord(String lat, String lon, String units);

    Observable<HashMap<String,String>> getDailyWeatherDataByCoord(String lat, String lon, String units);

}
