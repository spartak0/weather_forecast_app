package com.example.weather.domain.mapper;

import android.util.Log;

import com.example.weather.data.db.entity.forecast_detail.Current;
import com.example.weather.data.db.entity.forecast_detail.Daily;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.db.entity.forecast_detail.Hourly;
import com.example.weather.data.db.entity.forecast_detail.Weather;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.utils.Mapper;

import java.nio.file.ClosedFileSystemException;

public class WeatherMapper implements Mapper<WeatherData, WeatherEntity> {
    @Override
    public WeatherData toDomain(WeatherEntity weatherEntity) {
        return new WeatherData(weatherEntity.getId(),weatherEntity.getName(),
                weatherEntity.getLat(), weatherEntity.getLon(), weatherEntity.isFavorite(), weatherEntity.isSecondDayForecast(),
                weatherEntity.getCurrent().getTemp(), weatherEntity.getTimezone());
    }

    @Override
    public WeatherEntity fromDomain(WeatherData weatherData) {
        return new WeatherEntity(weatherData.getId(), weatherData.getLan(), weatherData.getLon(),weatherData.getName(), weatherData.getTimezone(),
                weatherData.isFavorite(), weatherData.isSecondDayForecast() ,new Current(weatherData.getCurrentTemp(), new Weather[]{}), new Daily[]{}, new Hourly[]{});
    }
}
