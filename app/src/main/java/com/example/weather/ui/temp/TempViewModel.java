package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.utils.Constant;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TempViewModel extends ViewModel {

    @SuppressLint("CheckResult")
    public Observable<HashMap<String, String>> getDailyWeatherByCoord(double lat, double lon){
       return RepositoryImpl.getInstance().getDailyWeatherDataByCoord(""+lat,""+lon, "metric")
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
    }

    public WeatherData getWeatherById(int id) {
        return RepositoryImpl.getInstance().getWeatherById(id);
    }

    public Completable deleteWeather(WeatherData weatherData) {
        return RepositoryImpl.getInstance().deleteWeather(weatherData);
    }
}
