package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.Repository;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.utils.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TempViewModel extends ViewModel {

    MutableLiveData<Map<String, String>> liveData = new MutableLiveData<>(new HashMap<String, String>());
    MutableLiveData<WeatherData> weatherDataLiveData = new MutableLiveData<WeatherData>();

    public MutableLiveData<WeatherData> getWeatherDataLiveData() {
        return weatherDataLiveData;
    }



    public LiveData<Map<String, String>> getLiveData(){
        return liveData;
    }


    @SuppressLint("CheckResult")
    public Observable<HashMap<String, String>> getDailyWeatherByCoord(double lat, double lon, int day){
       return RepositoryImpl.getInstance().getDailyWeatherDataByCoord(""+lat,""+lon,day , "metric")
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<WeatherData> getWeatherById(int id) {
        return RepositoryImpl.getInstance().getWeatherById(id)
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
            getDailyWeatherByCoord(weatherData.getLan(), weatherData.getLon(),0)
                    .subscribe(hashMap -> {
                        Map<String, String> tmp = liveData.getValue();
                        tmp.put(Constant.MORN, hashMap.get(Constant.MORN_MAPPER));
                        tmp.put(Constant.DAY, hashMap.get(Constant.DAY_MAPPER));
                        tmp.put(Constant.EVE , hashMap.get(Constant.EVE_MAPPER));
                        tmp.put(Constant.NIGHT, hashMap.get(Constant.NIGHT_MAPPER));
                        tmp.put(Constant.DAILY_ICON, hashMap.get(Constant.DAILY_ICON_MAPPER));
                        liveData.postValue(tmp);
                    }, Throwable::printStackTrace);
            getDailyWeatherByCoord(weatherData.getLan(), weatherData.getLon(),1)
                    .subscribe(hashMap -> {
                        Map<String, String> tmp = liveData.getValue();
                        tmp.put(Constant.MORN_2, hashMap.get(Constant.MORN_MAPPER));
                        tmp.put(Constant.DAY_2, hashMap.get(Constant.DAY_MAPPER));
                        tmp.put(Constant.EVE_2 , hashMap.get(Constant.EVE_MAPPER));
                        tmp.put(Constant.NIGHT_2, hashMap.get(Constant.NIGHT_MAPPER));
                        tmp.put(Constant.DAILY_ICON_2, hashMap.get(Constant.DAILY_ICON_MAPPER));
                        liveData.postValue(tmp);
                    }, Throwable::printStackTrace);
        });


    }


    @SuppressLint("CheckResult")
    public void fetchWeatherData(int id) {
        RepositoryImpl.getInstance().getWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherData -> {
                            weatherDataLiveData.setValue(weatherData);
                        });
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
}
