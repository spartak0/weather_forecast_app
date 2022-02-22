package com.example.weather.ui.forecast;

import android.annotation.SuppressLint;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ForecastViewModel extends ViewModel {


    LiveData<List<WeatherData>> liveData = fetchAllSavedWeather();

    public LiveData<List<WeatherData>> getLiveData() {
        return liveData;
    }

    private LiveData<List<WeatherData>> fetchAllSavedWeather(){
        return RepositoryImpl.getInstance().getAllWeather();
    }

    @SuppressLint("CheckResult")
    public void getCurrentWeatherByCoord(double lat, double lon, TextView textView){
        RepositoryImpl.getInstance().getCurrentWeatherDataByCoord(""+lat,""+lon, "metric")
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
}
