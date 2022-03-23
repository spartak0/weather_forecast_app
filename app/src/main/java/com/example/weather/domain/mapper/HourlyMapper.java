package com.example.weather.domain.mapper;

import android.util.Log;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.db.entity.forecast_detail.Hourly;
import com.example.weather.utils.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.Triple;

public class HourlyMapper implements Mapper<List<Triple<String,String,String>>,WeatherEntity> {

    @Override
    public ArrayList<Triple<String, String, String>> toDomain(WeatherEntity weatherEntity) {
        ArrayList<Triple<String, String, String>> list = new ArrayList<Triple<String,String,String>>();
        Hourly[] hourlies = weatherEntity.getHourly();
        for (Hourly hourly : hourlies) {
            Date date= new Date(hourly.getDt()*10*10*10);
            Triple triple = new Triple(date.getHours()+":"+date.getMinutes(), Math.round(hourly.getTemp())+"°", hourly.getWeather()[0].getIcon());
            list.add(triple);
        }
        return list;
    }

    @Override
    public WeatherEntity fromDomain(List<Triple<String, String, String>> triples) {
        return null;
    }
}
