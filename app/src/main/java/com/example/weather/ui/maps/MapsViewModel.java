package com.example.weather.ui.maps;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.db.entity.ForecastData;
import com.example.weather.data.db.entity.ForecastDetail.Coord;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.ui.Forecast.ForecastActivity;
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


    @SuppressLint("CheckResult")
    public void getWeatherDataByCoord(Intent intent){
        forecastApi.getWeatherDataByCoord(""+marker.getLat(),""+marker.getLon(),
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ForecastData>() {
                    @Override
                    public void accept(@NonNull ForecastData forecastData) throws Exception {
                        String locationName= intent.getStringExtra(Constant.locationName);
                        Log.d("A", "accept: "+marker.getLat()+"\t"+marker.getLon());
                        Log.d("A", "accept: "+ locationName+"\t"+forecastData.getMain().getTemp());
                        //todo изменить лайв дату.
                    }

                });
    }
}
