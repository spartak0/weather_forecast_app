package com.example.weather.ui.forecasts.favorite_forecast;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.forecast.WeatherData;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteForecastViewModel extends ViewModel {
    CompositeDisposable disposable = new CompositeDisposable();
    MutableLiveData<Map<Integer, WeatherData>> liveData = new MutableLiveData<>(new HashMap<Integer, WeatherData>());

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }

    public LiveData<Map<Integer, WeatherData>> getLiveData() {
        return liveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fetchFavoriteSavedWeather() {
        disposable.add(RepositoryImpl.getInstance().getFavoriteWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherData -> {
                    weatherData.forEach(this::fetchCurrentWeather);
                    weatherData.forEach(this::fetchTimeZone);
                    Map<Integer, WeatherData> map = new HashMap<>();
                    weatherData.forEach(weatherData1 -> {
                        map.put(weatherData1.getId(), weatherData1);
                    });
                    liveData.setValue(map);
                }, Throwable::printStackTrace));
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
    private void fetchTimeZone(WeatherData weatherData) {
        RepositoryImpl.getInstance().getTimezone(weatherData.getLan()+"",weatherData.getLon()+"")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Map<Integer, WeatherData> forecasts = liveData.getValue();
                    if (forecasts != null) {
                        weatherData.setTimezone(s);
                        forecasts.put(weatherData.getId(), weatherData);
                    }
                    liveData.setValue(forecasts);
                },Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    public void update(WeatherData weatherData){
        RepositoryImpl.getInstance().updateWeather(weatherData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{},Throwable::printStackTrace);
    }
}
