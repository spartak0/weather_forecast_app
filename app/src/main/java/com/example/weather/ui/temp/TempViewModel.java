package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.utils.Constant;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TempViewModel extends ViewModel {

    @SuppressLint("CheckResult")
    public void getDailyWeatherByCoord(double lat, double lon,TextView morn, TextView day, TextView eve, TextView night){
        RepositoryImpl.getInstance().getDailyWeatherDataByCoord(""+lat,""+lon, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HashMap<String, Float>>() {
                    @Override
                    public void accept(HashMap<String, Float> stringFloatHashMap) throws Exception {
                            morn.setText(""+stringFloatHashMap.get(Constant.morn));
                            day.setText(""+stringFloatHashMap.get(Constant.day));
                            eve.setText(""+stringFloatHashMap.get(Constant.eve));
                            night.setText(""+stringFloatHashMap.get(Constant.night));
                    }
                });
    }

    public WeatherData getWeatherById(int id) {
        return RepositoryImpl.getInstance().getWeatherById(id);
    }

    public void deleteWeather(WeatherData weatherData) {
        RepositoryImpl.getInstance().deleteWeather(weatherData);
    }
}
