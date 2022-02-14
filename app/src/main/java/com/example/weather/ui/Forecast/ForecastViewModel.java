package com.example.weather.ui.Forecast;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.db.database.WeatherDatabase;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.utils.Constant;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForecastViewModel extends AndroidViewModel {


    LiveData<List<WeatherEntity>> liveData = WeatherDatabase.getInstance(getApplication().getApplicationContext()).weatherDao().getAllWeather();

    public ForecastViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<WeatherEntity>> getLiveData() {
        return liveData;
    }

}
