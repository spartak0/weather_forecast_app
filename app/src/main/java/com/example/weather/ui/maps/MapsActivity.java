package com.example.weather.ui.maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.weather.R;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.databinding.ActivityMapsBinding;
import com.example.weather.data.db.entity.ForecastData;
import com.example.weather.data.db.entity.ForecastDetail.Coord;
import com.example.weather.ui.Forecast.ForecastActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ForecastApi forecastApi= ForecastApi.Instance.getForecastApi();
    Coord marker=new Coord();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                forecastApi.getWeatherDataByCoord(""+marker.getLat(),""+marker.getLon(),
                        "6d0dbf434f8a30b4004d1a5cdf685d57", "metric")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ForecastData>() {
                            @Override
                            public void accept(@NonNull ForecastData forecastData) throws Exception {
                                String locationName= getIntent().getStringExtra("locationName");
                                Toast.makeText(getApplicationContext(), ""+ forecastData.getMain().getTemp()+" "+locationName, Toast.LENGTH_SHORT).show();
                                //todo изменить лайв дату.
                            }
                        });
                Intent intent = new Intent(getApplicationContext(), ForecastActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                System.out.println(latLng.latitude+"\t"+latLng.longitude);
                marker.setLat(latLng.latitude);
                marker.setLon(latLng.longitude);
            }
        });
    }
}