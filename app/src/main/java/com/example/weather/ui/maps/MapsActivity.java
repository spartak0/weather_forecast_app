package com.example.weather.ui.maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.R;
import com.example.weather.data.db.database.WeatherDatabase;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.databinding.ActivityMapsBinding;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.ui.Forecast.ForecastActivity;
import com.example.weather.utils.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ForecastApi forecastApi= ForecastApi.Instance.getForecastApi();
    private MapsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewModel = new ViewModelProvider(this).get(MapsViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                //todo здесь должны быть не ентити
                String locationName= getIntent().getStringExtra(Constant.locationName);
                WeatherEntity weatherEntity= new WeatherEntity(locationName,viewModel.getMarkerLat(),viewModel.getMarkerLon());
                WeatherDatabase.getInstance(getApplication().getApplicationContext()).
                        weatherDao().addWeather(weatherEntity);
                Intent intent = new Intent(getApplicationContext(), ForecastActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setDefaultMarker();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
                viewModel.setMarker(latLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                Log.d("A", "onMapClick:"+latLng.latitude+"\t"+latLng.longitude);
            }
        });
    }

    private void setDefaultMarker() {
        mMap.clear();
        LatLng latLng=new LatLng(Constant.moscowLan, Constant.moscowLon);
        mMap.addMarker(new MarkerOptions().position(latLng));
        viewModel.setMarker(latLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}