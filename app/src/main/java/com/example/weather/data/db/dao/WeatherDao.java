package com.example.weather.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weather.data.db.entity.WeatherEntity;

import java.util.List;

import io.reactivex.Completable;

@Dao
public
interface WeatherDao {
    @Query("SELECT * FROM WeatherEntity")
    LiveData<List<WeatherEntity>> getAllWeather();

    @Query("SELECT * FROM WeatherEntity WHERE id=:id")
    WeatherEntity getWeatherById(int id);

    @Insert
    void addWeather(WeatherEntity weatherEntity);

    @Delete
    void deleteWeather(WeatherEntity weatherEntity);

    @Update
    void updateWeather(WeatherEntity weatherEntity);

}
