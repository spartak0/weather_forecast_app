package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.forecast.WeatherData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.Pair;
import kotlin.Triple;

public class TempViewModel extends ViewModel {
    MutableLiveData<ArrayList<Triple<String,String,String>>> hourlyLiveData =new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<WeatherData> weatherDataLiveData = new MutableLiveData<>();
    MutableLiveData<List<Pair<Float,String>>> dailyLiveData = new MutableLiveData<>(new ArrayList<>());
    CompositeDisposable disposable = new CompositeDisposable();


    @SuppressLint("CheckResult")
    public void fetchHourlyWeather(int id){
        disposable.add(
                getWeatherById(id).subscribe(weatherData -> {
                    getHourlyWeatherByCoord(weatherData.getLan(), weatherData.getLon())
                            .subscribe(list -> hourlyLiveData.postValue(list));
                }));
    }

    @SuppressLint("CheckResult")
    public void fetchWeatherData(int id) {
        RepositoryImpl.getInstance().getWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherData -> {
                    weatherDataLiveData.setValue(weatherData);
                }, Throwable::printStackTrace);
    }
    @SuppressLint("CheckResult")
    public void fetchDailyWeather(int id){
            getWeatherById(id).subscribe(weatherData -> {
                getDailyWeatherData(""+weatherData.getLan(),""+weatherData.getLon(),0)
                        .subscribe(pair -> {
                            List<Pair<Float,String>> pairList= dailyLiveData.getValue();
                            pairList.add(0,pair);
                            dailyLiveData.setValue(pairList);
                        },Throwable::printStackTrace);

                getDailyWeatherData(""+weatherData.getLan(),""+weatherData.getLon(),1)
                        .subscribe(pair -> {
                            List<Pair<Float,String>> pairList= dailyLiveData.getValue();
                            pairList.add(1,pair);
                            dailyLiveData.setValue(pairList);
                        },Throwable::printStackTrace);
            },Throwable::printStackTrace);
    }

    public MutableLiveData<List<Pair<Float, String>>> getDailyLiveData() {
        return dailyLiveData;
    }

    public MutableLiveData<WeatherData> getWeatherDataLiveData() {
        return weatherDataLiveData;
    }

    public MutableLiveData<ArrayList<Triple<String, String, String>>> getHourlyLiveData() {
        return hourlyLiveData;
    }


    private Observable<WeatherData> getWeatherById(int id) {
        return RepositoryImpl.getInstance().getWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Pair<Float,String>> getDailyWeatherData(String lan, String lon, int day){
        return RepositoryImpl.getInstance().getDailyWeatherDataByCoord(lan,lon, day, "metric")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<ArrayList<Triple<String,String,String>>> getHourlyWeatherByCoord(double lat, double lon){
        return RepositoryImpl.getInstance().getHourlyWeatherByCoord(""+lat,""+lon, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public void revertIsSecondDailyForecast() {
        WeatherData weatherData = weatherDataLiveData.getValue();
        weatherData.setSecondDayForecast(!weatherData.isSecondDayForecast());
        weatherDataLiveData.setValue(weatherData);
    }
    @SuppressLint("CheckResult")
    public void update() {
        RepositoryImpl.getInstance().updateWeather(weatherDataLiveData.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @SuppressLint("CheckResult")
    public void deleteWeatherById(int id){
        RepositoryImpl.getInstance().deleteWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{},Throwable::printStackTrace);
    }
}