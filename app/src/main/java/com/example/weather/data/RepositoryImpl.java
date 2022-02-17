package com.example.weather.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.weather.data.db.database.WeatherDatabase;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.domain.mapper.WeatherMapper;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.domain.Repository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class RepositoryImpl implements Repository {
    final WeatherMapper weatherMapper= new WeatherMapper();
    Context context;
    @SuppressLint("StaticFieldLeak")
    static RepositoryImpl instance;


    public RepositoryImpl(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public LiveData<List<WeatherData>> getAllWeather() {
        return Transformations.map(WeatherDatabase.getInstance(context).weatherDao().getAllWeather(),
                x -> {
            return x.stream().map(weatherMapper::toDomain).collect(Collectors.toList());
                });
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
    public Observable<Float> getWeatherDataByCoord(String lat, String lon, String units) {
        Observable<Float> floatObservable= ForecastApi.Instance.getForecastApi().getWeatherDataByCoord(lat, lon, units).map(
                new Function<WeatherEntity, Float>() {
                    @Override
                    public Float apply(@NonNull WeatherEntity weatherEntity) throws Exception {
                        Float tmp = weatherEntity.getMain().getTemp();
                        return weatherEntity.getMain().getTemp();
                    }
                }
        );
        return floatObservable;
    }


    public static RepositoryImpl getInstance() {
        return instance;
    }
    public static void Init(Context context) {
        instance= new RepositoryImpl(context);
    }



}
