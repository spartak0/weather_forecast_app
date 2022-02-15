package com.example.weather.domain.mapper;

import com.example.weather.data.db.entity.ForecastDetail.Coord;
import com.example.weather.data.db.entity.ForecastDetail.Main;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.utils.Mapper;

public class WeatherMapper implements Mapper<WeatherData, WeatherEntity> {
    @Override
    public WeatherData toDomain(WeatherEntity weatherEntity) {
        return new WeatherData(weatherEntity.getId(),weatherEntity.getName(),
                weatherEntity.getCoord().getLat(), weatherEntity.getCoord().getLon());
    }

    @Override
    public WeatherEntity fromDomain(WeatherData weatherData) {
        Coord coord = new Coord(weatherData.getLan(),weatherData.getLon());
        return new WeatherEntity(weatherData.getId(),coord,new Main(), weatherData.getName() );
    }
}
