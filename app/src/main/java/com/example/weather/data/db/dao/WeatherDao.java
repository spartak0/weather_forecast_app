package com.example.weather.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weather.data.db.entity.WeatherEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public
interface WeatherDao {
    @Query("SELECT * FROM WeatherEntity")
    Observable<List<WeatherEntity>> getAllWeather();

    @Query("SELECT * FROM WeatherEntity WHERE id=:id")
    WeatherEntity getWeatherById(int id);

    @Insert
    Completable addWeather(WeatherEntity weatherEntity);

    @Delete
    Completable deleteWeather(WeatherEntity weatherEntity);

    @Update
    Completable updateWeather(WeatherEntity weatherEntity);

}
