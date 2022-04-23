package com.example.weather.domain.mapper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.db.entity.forecast_detail.Hourly;
import com.example.weather.utils.Mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import kotlin.Triple;

public class HourlyMapper implements Mapper<List<Triple<String,String,String>>,WeatherEntity> {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ArrayList<Triple<String, String, String>> toDomain(WeatherEntity weatherEntity) {
        ArrayList<Triple<String, String, String>> list = new ArrayList<Triple<String,String,String>>();
        Hourly[] hourlies = weatherEntity.getHourly();
        for (Hourly hourly : hourlies) {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(hourly.getDt()*1000);
            DateFormat dateFormat=new SimpleDateFormat("HH:mm");
            dateFormat.setTimeZone(TimeZone.getTimeZone(weatherEntity.getTimezone()));
            Triple triple = new Triple(dateFormat.format(date.getTime()), Math.round(hourly.getTemp())+"°", hourly.getWeather()[0].getIcon());
            list.add(triple);
        }
        return list;
    }

    @Override
    public WeatherEntity fromDomain(List<Triple<String, String, String>> triples) {
        return null;
    }
}
