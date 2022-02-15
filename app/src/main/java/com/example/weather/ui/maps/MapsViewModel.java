package com.example.weather.ui.maps;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;

import com.example.weather.data.db.database.WeatherDatabase;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.db.entity.ForecastDetail.Coord;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.utils.Constant;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsViewModel extends ViewModel {

    Coord marker=new Coord();
    ForecastApi forecastApi= ForecastApi.Instance.getForecastApi();



    public void setMarker(LatLng latLng) {
        marker.setLat(latLng.latitude);
        marker.setLon(latLng.longitude);
    }

    public double getMarkerLat() {
        return marker.getLat();
    }
    public double getMarkerLon() {
        return marker.getLon();
    }
}
