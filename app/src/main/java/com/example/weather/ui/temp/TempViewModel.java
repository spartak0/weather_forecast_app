package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.weather.R;
import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.utils.Constant;
import com.example.weather.utils.DailyMapper;
import com.example.weather.utils.SettingManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
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
                            .subscribe(list -> hourlyLiveData.postValue(list),Throwable::printStackTrace);
                }, Throwable::printStackTrace));
    }

    @SuppressLint("CheckResult")
    public void fetchWeatherData(int id) {
        disposable.add(
            RepositoryImpl.getInstance().getWeatherById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherData -> {
                        weatherDataLiveData.setValue(weatherData);
                    }, Throwable::printStackTrace));
    }
    @SuppressLint("CheckResult")
    public void fetchDailyWeather(int id){
        disposable.add(
            getWeatherById(id).subscribe(weatherData -> {
                getDailyWeatherData(""+weatherData.getLan(),""+weatherData.getLon(),0)
                        .subscribe(pair -> {
                            List<Pair<Float,String>> pairList= dailyLiveData.getValue();
                            pairList.add(0,pair);
                            dailyLiveData.setValue(pairList);
                            getDailyWeatherData(""+weatherData.getLan(),""+weatherData.getLon(),1)
                                    .subscribe(pair2 -> {
                                        List<Pair<Float,String>> pairList2= dailyLiveData.getValue();
                                        pairList.add(1,pair2);
                                        dailyLiveData.setValue(pairList2);
                                    },Throwable::printStackTrace);
                        },Throwable::printStackTrace);
            },Throwable::printStackTrace));
    }
    @SuppressLint("CheckResult")
    public void fetchNoNetwork(int id){
        RepositoryImpl.getInstance().getWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherData -> {
                    ArrayList<Triple<String,String,String>> list = new ArrayList<>();
                    Calendar calendar= Calendar.getInstance();
                    DateFormat dateFormat=new SimpleDateFormat("HH:00");
                    Log.d("TAG", "fetchNoNetwork: "+weatherData.getTimezone());
                    dateFormat.setTimeZone(TimeZone.getTimeZone(weatherData.getTimezone()));
                    for(int i =0;i<48;i++){
                        Triple<String,String,String> triple= new Triple<String,String,String>(dateFormat.format(calendar.getTime()), "","");
                        list.add(triple);
                        calendar.add(Calendar.HOUR,1);
                    }
                    hourlyLiveData.postValue(list);
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
        return RepositoryImpl.getInstance().getDailyWeatherDataByCoord(lan,lon, day)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<ArrayList<Triple<String,String,String>>> getHourlyWeatherByCoord(double lat, double lon){
        return RepositoryImpl.getInstance().getHourlyWeatherByCoord(""+lat,""+lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public void revertIsSecondDailyForecast() {
        WeatherData weatherData = weatherDataLiveData.getValue();
        assert weatherData != null;
        weatherData.setSecondDayForecast(!weatherData.isSecondDayForecast());
        weatherDataLiveData.setValue(weatherData);
    }
    @SuppressLint("CheckResult")
    public void update() {
        disposable.add(
            RepositoryImpl.getInstance().updateWeather(weatherDataLiveData.getValue())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->{},Throwable::printStackTrace));
    }

    @SuppressLint("CheckResult")
    public void deleteWeatherById(int id){
        disposable.add(
            RepositoryImpl.getInstance().deleteWeatherById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->{},Throwable::printStackTrace));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public Boolean isNetworkAvailable(Context context) {
        return RepositoryImpl.getInstance().isNetworkAvailable(context);
    }
}