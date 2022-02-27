package com.example.weather.domain;

import androidx.lifecycle.LiveData;

import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public interface Repository {

    LiveData<List<WeatherData>> getAllWeather();

    WeatherData getWeatherById(int id);

    void addWeather(WeatherData weatherData);

    void deleteWeather(WeatherData weatherData);

    void updateWeather(WeatherData weatherData);

    Observable<Float> getCurrentWeatherDataByCoord(String lat, String lon, String units);

    Observable<HashMap<String,String>> getDailyWeatherDataByCoord(String lat, String lon, String units);

}
