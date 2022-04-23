package com.example.weather.domain.mapper;

import com.example.weather.data.db.entity.WeatherEntity;

import kotlin.Pair;

public class DailyMapper implements com.example.weather.utils.DailyMapper<Pair<Float,String>, WeatherEntity> {


    @Override
    public Pair<Float, String> toDomain(WeatherEntity weatherEntity, int day) {

        return new Pair<Float,String>(weatherEntity.getDaily()[day].getTemp().getDay(), weatherEntity.getDaily()[day].getWeather()[0].getIcon());
    }

    @Override
    public WeatherEntity fromDomain(Pair<Float, String> pair) {
        return null;
    }
}