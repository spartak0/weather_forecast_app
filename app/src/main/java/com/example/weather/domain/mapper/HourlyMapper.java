package com.example.weather.domain.mapper;

import static android.content.Context.MODE_PRIVATE;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.db.entity.forecast_detail.Hourly;
import com.example.weather.utils.Mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import kotlin.Triple;

public class HourlyMapper implements Mapper<List<Triple<String,String,String>>,WeatherEntity> {
    private Context context;
    public HourlyMapper(Context context) {
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ArrayList<Triple<String, String, String>> toDomain(WeatherEntity weatherEntity) {
        ArrayList<Triple<String, String, String>> list = new ArrayList<Triple<String,String,String>>();
        Hourly[] hourlies = weatherEntity.getHourly();
        SharedPreferences preferences = this.context.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("MyLang","");
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
