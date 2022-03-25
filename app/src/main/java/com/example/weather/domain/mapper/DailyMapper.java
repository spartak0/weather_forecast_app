package com.example.weather.domain.mapper;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.db.entity.forecast_detail.Daily;
import com.example.weather.data.db.entity.forecast_detail.Temp;
import com.example.weather.utils.Constant;

import java.util.HashMap;

public class DailyMapper implements com.example.weather.utils.DailyMapper<HashMap<String, String>, WeatherEntity> {

    @Override
    public HashMap<String, String> toDomain(WeatherEntity weatherEntity,int day) {
        HashMap<String,String> dailyForecast= new HashMap<>();
        dailyForecast.put(Constant.MORN_MAPPER, weatherEntity.getDaily()[day].getTemp().getMorn()+"");
        dailyForecast.put(Constant.DAY_MAPPER, weatherEntity.getDaily()[day].getTemp().getDay()+"");
        dailyForecast.put(Constant.EVE_MAPPER, weatherEntity.getDaily()[day].getTemp().getEve()+"");
        dailyForecast.put(Constant.NIGHT_MAPPER, weatherEntity.getDaily()[day].getTemp().getNight()+"");
        dailyForecast.put(Constant.DAILY_ICON_MAPPER, weatherEntity.getDaily()[day].getWeather()[0].getIcon());

        return dailyForecast;
    }

    @Override
    public WeatherEntity fromDomain(HashMap<String, String> stringFloatHashMap) {
        Temp temp= new Temp(Float.parseFloat(stringFloatHashMap.get(Constant.DAY)),
                Float.parseFloat(stringFloatHashMap.get(Constant.EVE)),
                Float.parseFloat(stringFloatHashMap.get(Constant.MORN)),
                Float.parseFloat(stringFloatHashMap.get(Constant.NIGHT)));
        Daily[] daily = new Daily[]{};
        daily[0].setTemp(temp);
        return new WeatherEntity(0,0,0,null,false, false,null,daily,null);
    }
}
