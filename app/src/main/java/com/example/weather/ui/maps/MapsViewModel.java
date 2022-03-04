package com.example.weather.ui.maps;

import androidx.lifecycle.ViewModel;

import com.example.weather.data.network.api.ForecastApi;
import com.google.android.gms.maps.model.LatLng;

public class MapsViewModel extends ViewModel {

    Float latMarker;
    Float lonMarker;



    public void setMarker(LatLng latLng) {
        latMarker =(float) latLng.latitude;
         lonMarker=(float) latLng.longitude;
    }

    public float getMarkerLat() {
        return latMarker;
    }
    public float getMarkerLon() {
        return lonMarker;
    }
}
