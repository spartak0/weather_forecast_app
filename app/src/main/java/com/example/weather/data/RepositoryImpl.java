package com.example.weather.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.weather.data.db.database.WeatherDatabase;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.domain.Repository;
import com.example.weather.domain.mapper.DailyMapper;
import com.example.weather.domain.mapper.WeatherMapper;
import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class RepositoryImpl implements Repository {
    final WeatherMapper weatherMapper= new WeatherMapper();
    final DailyMapper dailyMapper= new DailyMapper();
    Context context;
    @SuppressLint("StaticFieldLeak")
    static RepositoryImpl instance;


    public RepositoryImpl(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Observable<List<WeatherData>> getAllWeather() {
        return WeatherDatabase.getInstance(context).weatherDao().getAllWeather()
                .map((Function<List<WeatherEntity>, List<WeatherData>>) weatherEntities -> weatherEntities.stream().map(new java.util.function.Function<WeatherEntity, WeatherData>() {
                    @Override
                    public WeatherData apply(WeatherEntity weatherEntity) {
                        return weatherMapper.toDomain(weatherEntity);
                    }
                }).collect(Collectors.toList()));
    }

    @Override
    public WeatherData getWeatherById(int id) {
        WeatherEntity tmp = WeatherDatabase.getInstance(context).weatherDao().getWeatherById(id);
        return weatherMapper.toDomain( tmp);
    }


    @Override
    public void addWeather(WeatherData weatherData) {
        WeatherDatabase.getInstance(context).weatherDao().addWeather(weatherMapper.fromDomain(weatherData));
    }


    @Override
    public void deleteWeather(WeatherData weatherData) {
        WeatherDatabase.getInstance(context).weatherDao().deleteWeather(weatherMapper.fromDomain(weatherData));
    }

    @Override
    public void updateWeather(WeatherData weatherData) {
        WeatherDatabase.getInstance(context).weatherDao().updateWeather(weatherMapper.fromDomain(weatherData));
    }

    @Override
    public Observable<Float> getCurrentWeatherDataByCoord(String lat, String lon, String units) {
        Observable<Float> floatObservable= ForecastApi.Instance.getForecastApi().getWeatherDataByCoord(lat, lon, units).map(
                new Function<WeatherEntity, Float>() {
                    @Override
                    public Float apply(@NonNull WeatherEntity weatherEntity) throws Exception {
                        return weatherEntity.getCurrent().getTemp();
                    }
                }
        );
        return floatObservable;
    }

    @Override
    public Observable<HashMap<String, String>> getDailyWeatherDataByCoord(String lat, String lon, String units) {
        Observable<HashMap<String, String>> floatObservable= ForecastApi.Instance.getForecastApi().getWeatherDataByCoord(lat, lon, units).map(
                new Function<WeatherEntity, HashMap<String, String>>() {
                    @Override
                    public HashMap<String, String> apply(@NonNull WeatherEntity weatherEntity) throws Exception {
                        return dailyMapper.toDomain(weatherEntity);
                    }
                });
        return floatObservable;
    }


    public static RepositoryImpl getInstance() {
        return instance;
    }
    public static void init(Context context) {
        instance= new RepositoryImpl(context);
    }



}
