package com.example.weather.ui.forecast;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ForecastViewModel extends ViewModel {

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
                    weatherData.forEach(weatherData1 -> {
                        fetchCurrentWeather(weatherData1);
                    });
                    Map<Integer, WeatherData> map = new HashMap<>();
                    for (WeatherData weatherData1: weatherData){
                        map.put(weatherData1.getId(), weatherData1);
                    }
                    liveData.setValue(map);
                }, Throwable::printStackTrace));
    }

    private void fetchCurrentWeather(WeatherData weatherData) {
        disposable.add(
                RepositoryImpl.getInstance().getCurrentWeatherDataByCoord("" + weatherData.getLan(), "" + weatherData.getLon(), "metric")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(temp -> {
                            Map<Integer, WeatherData> forecasts = liveData.getValue();
                            if (forecasts != null) {
                                weatherData.setTemperature(temp);
                                forecasts.put(weatherData.getId(), weatherData);
                            }
                            liveData.setValue(forecasts);
                        })
        );
    }
}
