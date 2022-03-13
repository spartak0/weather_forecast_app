package com.example.weather.ui.temp;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.forecast.WeatherData;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TempViewModel extends ViewModel {

    MutableLiveData<Map<String, String>> liveData = new MutableLiveData<>(new HashMap<String, String>());

    public LiveData<Map<String, String>> getLiveData(){
        return liveData;
    }


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
    public Completable deleteWeatherById(int id){
        return RepositoryImpl.getInstance().deleteWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    public void fetchDailyWeather(int id) {
        getWeatherById(id).subscribe(weatherData -> {
            getDailyWeatherByCoord(weatherData.getLan(), weatherData.getLon())
                    .subscribe(hashMap -> {
                        liveData.postValue(hashMap);
                    }, Throwable::printStackTrace);
        });

    }
}
