package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.forecast.WeatherData;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TempViewModel extends ViewModel {

    MutableLiveData<HashMap<String, String>> forecast = new MutableLiveData(new HashMap<>());

    @SuppressLint("CheckResult")
    public Observable<HashMap<String, String>> getDailyWeatherByCoord(double lat, double lon){
       return RepositoryImpl.getInstance().getDailyWeatherDataByCoord(""+lat,""+lon, "metric")
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<WeatherData> getWeatherById(int id) {
        return RepositoryImpl.getInstance().getWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Completable deleteWeather(WeatherData weatherData) {
        return RepositoryImpl.getInstance().deleteWeather(weatherData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    public void deleteWeatherById(int id) throws InterruptedException {
        getWeatherById(id).subscribe(weatherData -> {

             deleteWeather(weatherData)
                        .subscribe(() -> {
                        }, Throwable::printStackTrace);
            });
        TimeUnit.MILLISECONDS.sleep(1);
    }

    @SuppressLint("CheckResult")
    public void bindDailyWeather(Context context, int id, ImageView dailyIcon, TextView tvMorningValue, TextView tvDayValue, TextView tvEveValue, TextView tvNightValue) {
        getWeatherById(id).subscribe(weatherData -> {
            getDailyWeatherByCoord(weatherData.getLan(), weatherData.getLon())
                    .subscribe(hashMap -> {
                        forecast.postValue(hashMap);
                    }, Throwable::printStackTrace);
        });

    }
}
