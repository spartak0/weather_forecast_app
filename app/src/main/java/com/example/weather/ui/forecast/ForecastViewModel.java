package com.example.weather.ui.forecast;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;
import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ForecastViewModel extends ViewModel {


    LiveData<List<WeatherData>> liveData = fetchAllSavedWeather();

    public LiveData<List<WeatherData>> getLiveData() {
        return liveData;
    }

    private LiveData<List<WeatherData>> fetchAllSavedWeather(){
        return RepositoryImpl.getInstance().getAllWeather();
    }
}
