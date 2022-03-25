package com.example.weather.ui.temp;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.forecast.WeatherData;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.Pair;
import kotlin.Triple;

public class TempViewModel extends ViewModel {
    MutableLiveData<ArrayList<Triple<String,String,String>>> hourlyLiveData =new MutableLiveData<>(new ArrayList<Triple<String,String,String>>());
    MutableLiveData<Pair<Float,String>> currentWeather = new MutableLiveData<>();
    CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<ArrayList<Triple<String, String, String>>> getHourlyLiveData() {
        return hourlyLiveData;
    }

    public MutableLiveData<Pair<Float,String>> getCurrentWeather() {
        return currentWeather;
    }

    private Observable<ArrayList<Triple<String,String,String>>> getHourlyWeatherByCoord(double lat, double lon){
        return RepositoryImpl.getInstance().getHourlyWeatherByCoord(""+lat,""+lon, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    public void fetchHourlyWeather(int id){
        disposable.add(
        getWeatherById(id).subscribe(weatherData -> {
            getHourlyWeatherByCoord(weatherData.getLan(), weatherData.getLon())
                    .subscribe(list -> hourlyLiveData.postValue(list));
        }));
    }

    private Observable<WeatherData> getWeatherById(int id) {
        return RepositoryImpl.getInstance().getWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @SuppressLint("CheckResult")
    public void deleteWeatherById(int id){
        RepositoryImpl.getInstance().deleteWeatherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{},Throwable::printStackTrace);
    }

    private void getCurrentWeather(WeatherData weatherData) {
        disposable.add(
                RepositoryImpl.getInstance().getCurrentWeatherDataByCoord("" + weatherData.getLan(), "" + weatherData.getLon(), "metric")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pair -> {
                            currentWeather.postValue(pair);
                        }, Throwable::printStackTrace)
        );
    }

    @SuppressLint("CheckResult")
    public void fetchCurrentWeather(int id){
        disposable.add(
        getWeatherById(id).subscribe(this::getCurrentWeather,Throwable::printStackTrace));
    }
}
    //
//    MutableLiveData<ArrayList<HashMap<String, String>>> liveData = new MutableLiveData<ArrayList<HashMap<String, String>>>( new ArrayList<HashMap<String, String>>());
//    MutableLiveData<WeatherData> weatherDataLiveData = new MutableLiveData<WeatherData>();
//
//    public MutableLiveData<WeatherData> getWeatherDataLiveData() {
//        return weatherDataLiveData;
//    }
//
//
//
//    public LiveData<ArrayList<HashMap<String, String>>> getLiveData(){
//        return liveData;
//    }
//
//
//    @SuppressLint("CheckResult")
//    public Observable<HashMap<String, String>> getDailyWeatherByCoord(double lat, double lon, int day){
//       return RepositoryImpl.getInstance().getDailyWeatherDataByCoord(""+lat,""+lon,day , "metric")
//               .subscribeOn(Schedulers.io())
//               .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    private Observable<WeatherData> getWeatherById(int id) {
//        return RepositoryImpl.getInstance().getWeatherById(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//
//    @SuppressLint("CheckResult")
//    public Completable deleteWeatherById(int id){
//        return RepositoryImpl.getInstance().deleteWeatherById(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    @SuppressLint("CheckResult")
//    public void fetchDailyWeather(int id) {
//        getWeatherById(id).subscribe(weatherData -> {
//            getDailyWeatherByCoord(weatherData.getLan(), weatherData.getLon(),0)
//                    .subscribe(hashMap -> {
//                        ArrayList<HashMap<String,String>> tmp = liveData.getValue();
//                        HashMap<String,String> tmp2 =  new HashMap<>();
//                        tmp2.put(Constant.MORN, hashMap.get(Constant.MORN_MAPPER));
//                        tmp2.put(Constant.DAY, hashMap.get(Constant.DAY_MAPPER));
//                        tmp2.put(Constant.EVE , hashMap.get(Constant.EVE_MAPPER));
//                        tmp2.put(Constant.NIGHT, hashMap.get(Constant.NIGHT_MAPPER));
//                        tmp2.put(Constant.DAILY_ICON, hashMap.get(Constant.DAILY_ICON_MAPPER));
//                        tmp.add(0,tmp2);
//                        Log.d("TAG", "fetchDailyWeather: "+ tmp.get(0).get(Constant.DAY));
//                        liveData.postValue(tmp);
//                    }, Throwable::printStackTrace);
//            getDailyWeatherByCoord(weatherData.getLan(), weatherData.getLon(),1)
//                    .subscribe(hashMap -> {
//                        ArrayList<HashMap<String,String>> tmp = liveData.getValue();
//                        HashMap<String,String> tmp2 =  new HashMap<>();
//                        tmp2.put(Constant.MORN, hashMap.get(Constant.MORN_MAPPER));
//                        tmp2.put(Constant.DAY, hashMap.get(Constant.DAY_MAPPER));
//                        tmp2.put(Constant.EVE , hashMap.get(Constant.EVE_MAPPER));
//                        tmp2.put(Constant.NIGHT, hashMap.get(Constant.NIGHT_MAPPER));
//                        tmp2.put(Constant.DAILY_ICON, hashMap.get(Constant.DAILY_ICON_MAPPER));
//                        tmp.add(1,tmp2);
//                        liveData.postValue(tmp);
//                    }, Throwable::printStackTrace);
//        });
//
//
//    }
//
//
//    @SuppressLint("CheckResult")
//    public void fetchWeatherData(int id) {
//        RepositoryImpl.getInstance().getWeatherById(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(weatherData -> {
//                            weatherDataLiveData.setValue(weatherData);
//                        });
//    }
//
//    public void revertIsSecondDailyForecast() {
//        WeatherData weatherData = weatherDataLiveData.getValue();
//        weatherData.setSecondDayForecast(!weatherData.isSecondDayForecast());
//        weatherDataLiveData.setValue(weatherData);
//    }
//
//    @SuppressLint("CheckResult")
//    public void update() {
//        RepositoryImpl.getInstance().updateWeather(weatherDataLiveData.getValue())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
//    }
