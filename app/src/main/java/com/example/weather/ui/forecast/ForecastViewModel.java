package com.example.weather.ui.forecast;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.List;

import io.reactivex.Observable;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ForecastViewModel extends ViewModel {


    LiveData<List<WeatherData>> liveData = fetchAllSavedWeather();

    public LiveData<List<WeatherData>> getLiveData() {
        return liveData;
    }

    public LiveData<List<WeatherData>> fetchAllSavedWeather(){
        return RepositoryImpl.getInstance().getAllWeather();
    }

    @SuppressLint("CheckResult")
    public Observable<Float> getCurrentWeatherByCoord(double lat, double lon){
       return RepositoryImpl.getInstance().getCurrentWeatherDataByCoord(""+lat,""+lon, "metric");
    }
}
