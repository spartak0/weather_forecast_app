package com.example.weather.data.db.converter;

import androidx.room.TypeConverter;

import com.example.weather.data.db.entity.forecast_detail.Current;
import com.example.weather.data.db.entity.forecast_detail.Weather;

public class CurrentConverter {
    @TypeConverter
    public float fromHobbies(Current current) {
        return current.getTemp();
    }

    @TypeConverter
    public Current toHobbies(float data) {
        return new Current(data, new Weather[]{});
    }
}
