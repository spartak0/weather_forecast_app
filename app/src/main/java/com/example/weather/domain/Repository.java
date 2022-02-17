package com.example.weather.domain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public interface Repository {

    LiveData<List<WeatherData>> getAllWeather();

    WeatherData getWeatherById(int id);

    void addWeather(WeatherData weatherData);

    void deleteWeather(WeatherData weatherData);

    void updateWeather(WeatherData weatherData);

    Observable<Float> getWeatherDataByCoord(String lat, String lon, String units);

}
