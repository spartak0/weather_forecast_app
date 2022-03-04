package com.example.weather.domain.mapper;

import com.example.weather.data.db.entity.ForecastDetail.Current;
import com.example.weather.data.db.entity.ForecastDetail.Daily;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.utils.Mapper;

public class WeatherMapper implements Mapper<WeatherData, WeatherEntity> {
    @Override
    public WeatherData toDomain(WeatherEntity weatherEntity) {
        return new WeatherData(weatherEntity.getId(),weatherEntity.getName(),
                weatherEntity.getLat(), weatherEntity.getLon());
    }

    @Override
    public WeatherEntity fromDomain(WeatherData weatherData) {
        return new WeatherEntity(weatherData.getId(), weatherData.getLan(), weatherData.getLon(),weatherData.getName(), new Current(), new Daily[]{});
    }
}
