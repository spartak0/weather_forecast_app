package com.example.weather.ui.maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.R;
import com.example.weather.data.RepositoryImpl;
import com.example.weather.databinding.ActivityMapsBinding;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.ui.main.MainActivity;
import com.example.weather.utils.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
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
                String locationName= getIntent().getStringExtra(Constant.LOCATION_NAME);
                Boolean isFavorite = getIntent().getBooleanExtra(Constant.IS_FAVORITE,false);
                Boolean secondDayForecast = getIntent().getBooleanExtra(Constant.SECOND_DAY_FORECAST, false);
                WeatherData weatherData= new WeatherData(locationName, viewModel.getMarkerLat(),viewModel.getMarkerLon(), isFavorite, secondDayForecast);
                RepositoryImpl.getInstance().addWeather(weatherData)
                        .subscribeOn(Schedulers.io())
                        .subscribe(()->{},Throwable::printStackTrace);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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
            }
        });
    }

    private void setDefaultMarker() {
        mMap.clear();
        LatLng latLng=new LatLng(Constant.MOSCOW_LAN, Constant.MOSCOW_LON);
        mMap.addMarker(new MarkerOptions().position(latLng));
        viewModel.setMarker(latLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}