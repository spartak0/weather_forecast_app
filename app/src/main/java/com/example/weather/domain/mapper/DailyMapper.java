package com.example.weather.domain.mapper;

import com.example.weather.data.db.entity.ForecastDetail.Daily;
import com.example.weather.data.db.entity.ForecastDetail.Temp;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.utils.Constant;
import com.example.weather.utils.Mapper;

import java.util.HashMap;

public class DailyMapper implements Mapper<HashMap<String, String>, WeatherEntity> {

    @Override
    public HashMap<String, String> toDomain(WeatherEntity weatherEntity) {
        HashMap<String,String> dailyForecast= new HashMap<>();
        dailyForecast.put(Constant.morn, weatherEntity.getDaily()[0].getTemp().getMorn()+"");
        dailyForecast.put(Constant.day, weatherEntity.getDaily()[0].getTemp().getDay()+"");
        dailyForecast.put(Constant.eve, weatherEntity.getDaily()[0].getTemp().getEve()+"");
        dailyForecast.put(Constant.night, weatherEntity.getDaily()[0].getTemp().getNight()+"");
        dailyForecast.put(Constant.dailyIcon, weatherEntity.getDaily()[0].getWeather()[0].getIcon());
        return dailyForecast;
    }

    @Override
    public WeatherEntity fromDomain(HashMap<String, String> stringFloatHashMap) {
        Temp temp= new Temp(Float.parseFloat(stringFloatHashMap.get(Constant.day)),
                Float.parseFloat(stringFloatHashMap.get(Constant.eve)),
                Float.parseFloat(stringFloatHashMap.get(Constant.morn)),
                Float.parseFloat(stringFloatHashMap.get(Constant.night)));
        Daily[] daily = new Daily[]{};
        daily[0].setTemp(temp);
        return new WeatherEntity(0,0,0,null,null,daily);
    }
}