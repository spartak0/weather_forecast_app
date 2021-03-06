package com.example.weather.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.weather.data.db.database.WeatherDatabase;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.domain.Repository;
import com.example.weather.domain.mapper.DailyMapper;
import com.example.weather.domain.mapper.HourlyMapper;
import com.example.weather.domain.mapper.WeatherMapper;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.utils.SettingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Observable;
import kotlin.Pair;
import kotlin.Triple;

public class RepositoryImpl implements Repository {
    final WeatherMapper weatherMapper= new WeatherMapper();
    final DailyMapper dailyMapper= new DailyMapper();
    final HourlyMapper hourlyMapper;
    SettingManager settingManager;
    Context context;
    @SuppressLint("StaticFieldLeak")
    static RepositoryImpl instance;


    public RepositoryImpl(Context context) {
        this.context = context;
        this.hourlyMapper=new HourlyMapper();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Observable<List<WeatherData>> getAllWeather() {
        return WeatherDatabase.getInstance(context).weatherDao().getAllWeather()
                .map(weatherEntities -> weatherEntities.stream().map(weatherMapper::toDomain).collect(Collectors.toList()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Observable<List<WeatherData>> getFavoriteWeather() {
        return WeatherDatabase.getInstance(context).weatherDao().getFavoriteWeather()
                .map(weatherEntities -> weatherEntities.stream().map(weatherMapper::toDomain).collect(Collectors.toList()));
    }

    @Override
    public Observable<WeatherData> getWeatherById(int id) {
        return WeatherDatabase.getInstance(context).weatherDao().getWeatherById(id).map(weatherMapper::toDomain);
    }


    @Override
    public Completable addWeather(WeatherData weatherData) {
        return WeatherDatabase.getInstance(context).weatherDao().addWeather(weatherMapper.fromDomain(weatherData));
    }


    @Override
    public Completable deleteWeather(WeatherData weatherData) {
        return WeatherDatabase.getInstance(context).weatherDao().deleteWeather(weatherMapper.fromDomain(weatherData));
    }

    @Override
    public Completable deleteWeatherById(int id) {
        return WeatherDatabase.getInstance(context).weatherDao().deleteWeatherById(id);
    }

    @Override
    public Completable updateWeather(WeatherData weatherData) {
        return WeatherDatabase.getInstance(context).weatherDao().updateWeather(weatherMapper.fromDomain(weatherData));
    }

    @Override
    public Observable<Pair<Float,String>> getCurrentWeatherDataByCoord(String lat, String lon) {
        Observable<Pair<Float,String>> tmp= ForecastApi.Instance.getForecastApi().getWeatherDataByCoord(lat, lon).map(
                weatherEntity -> new Pair<Float,String>(weatherEntity.getCurrent().getTemp(),weatherEntity.getCurrent().getWeather()[0].getIcon())
        );
        return tmp;
    }

    @Override
    public Observable<Pair<Float,String>> getDailyWeatherDataByCoord(String lat, String lon,int day) {
        Observable<Pair<Float,String>> floatObservable= ForecastApi.Instance.getForecastApi().getWeatherDataByCoord(lat, lon).map(
                weatherEntity -> dailyMapper.toDomain(weatherEntity, day));
        return floatObservable;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Observable<ArrayList<Triple<String, String, String>>> getHourlyWeatherByCoord(String lat, String lon) {
        Observable<ArrayList<Triple<String, String, String>>> tmp = ForecastApi.Instance.getForecastApi().getWeatherDataByCoord(lat,lon).map(
                hourlyMapper::toDomain);
        return tmp;
    }

    @Override
    public Observable<String> getTimezone(String lat, String lon) {
        return ForecastApi.Instance.getForecastApi().getWeatherDataByCoord(lat,lon)
                .map(WeatherEntity::getTimezone);
    }


    @Override
    public void loadLocale() {
        this.settingManager.loadLocale();
    }

    @Override
    public void setLocale(String lang) {
        this.settingManager.setLocale(lang);

    }

    @Override
    public Boolean isNetworkAvailable() {
        return settingManager.isNetworkAvailable();
    }


    public void initSettingsManager(Context context) {
        this.settingManager= new SettingManager(context);
    }

    public static RepositoryImpl getInstance() {
        return instance;
    }
    public static void init(Context context) {
        instance= new RepositoryImpl(context);
    }



}
