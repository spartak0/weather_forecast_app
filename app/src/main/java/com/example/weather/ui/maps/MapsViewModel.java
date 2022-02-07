package com.example.weather.ui.maps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.db.entity.ForecastData;
import com.example.weather.data.db.entity.ForecastDetail.Coord;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.ui.Forecast.ForecastActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsViewModel extends ViewModel {
    Coord marker=new Coord();
    private ForecastApi forecastApi= ForecastApi.Instance.getForecastApi();

    public void setMarker(Float lan, Float lon) {
        marker.setLat(lan);
        marker.setLon(lon);
    }

    @SuppressLint("CheckResult")
    void getForecast(Intent intent){

    }
}
