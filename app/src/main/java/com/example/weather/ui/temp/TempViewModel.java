package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.Forecast.WeatherData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TempViewModel extends ViewModel {



//    @SuppressLint("CheckResult")
//    public void getWeatherDataByCoord(double lat, double lon, TextView textView, Context context){
//        RepositoryImpl.getInstance(context).getWeatherDataByCoord(""+lat,""+lon,
//                "metric")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<WeatherData>() {
//                               @Override
//                               public void accept(WeatherData weatherData) throws Exception {
//                                   textView.setText("" + weatherData.ge);
//                               }
//                           });
//    }
    @SuppressLint("CheckResult")
    public void getWeatherByCoord(double lat, double lon, TextView textView){
        RepositoryImpl.getInstance().getWeatherDataByCoord(""+lat,""+lon, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Float>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(Float aFloat) throws Exception {
                        textView.setText(""+aFloat);
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
