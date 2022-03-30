package com.example.weather.ui.forecasts.all_forecast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.utils.SettingManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AllForecastViewModel extends ViewModel {

    CompositeDisposable disposable = new CompositeDisposable();
    MutableLiveData<Map<Integer, WeatherData>> liveData = new MutableLiveData(new HashMap<Integer, WeatherData>());


    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }

    public LiveData<Map<Integer, WeatherData>> getLiveData() {
        return liveData;
    }

    public void fetchAllSavedWeather() {
        disposable.add(RepositoryImpl.getInstance().getAllWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherData -> {
                    weatherData.forEach(this::fetchCurrentWeather);
                    weatherData.forEach(this::fetchTimeZone);
                    Map<Integer, WeatherData> map = new HashMap<>();
                    for (WeatherData weatherData1: weatherData){
                        map.put(weatherData1.getId(), weatherData1);
                    }
                    liveData.setValue(map);
                }, Throwable::printStackTrace));
    }

    @SuppressLint("CheckResult")
    private void fetchTimeZone(WeatherData weatherData) {
        disposable.add(
        RepositoryImpl.getInstance().getTimezone(weatherData.getLan()+"",weatherData.getLon()+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Map<Integer, WeatherData> forecasts = liveData.getValue();
                    if (forecasts != null) {
                        weatherData.setTimezone(s);
                        forecasts.put(weatherData.getId(), weatherData);
                    }
                    liveData.setValue(forecasts);
                },Throwable::printStackTrace));
    }

    private void fetchCurrentWeather(WeatherData weatherData) {
        disposable.add(
                RepositoryImpl.getInstance().getCurrentWeatherDataByCoord("" + weatherData.getLan(), "" + weatherData.getLon())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Pair -> {
                            Map<Integer, WeatherData> forecasts = liveData.getValue();
                            if (forecasts != null) {
                                weatherData.setCurrentTemp(Pair.getFirst());
                                forecasts.put(weatherData.getId(), weatherData);
                            }
                            liveData.setValue(forecasts);
                        },Throwable::printStackTrace)
        );
    }

    @SuppressLint("CheckResult")
    public void update(WeatherData weatherData){
        RepositoryImpl.getInstance().updateWeather(weatherData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{},Throwable::printStackTrace);
    }

    public void fetchAllSavedWeatherNotNetwork() {
        disposable.add(RepositoryImpl.getInstance().getAllWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherData -> {
                    Map<Integer, WeatherData> map = new HashMap<>();
                    for (WeatherData weatherData1: weatherData){
                        map.put(weatherData1.getId(), weatherData1);
                    }
                    liveData.setValue(map);
                }, Throwable::printStackTrace));
    }

    public void updateAll() {
        ArrayList<WeatherData> list=new ArrayList<>(liveData.getValue().values());
        list.forEach(this::update);
    }

    public Boolean isNetworkAvailable() {
       return RepositoryImpl.getInstance().isNetworkAvailable();
    }
}
