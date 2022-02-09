package com.example.weather.ui.maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.R;
import com.example.weather.data.network.api.ForecastApi;
import com.example.weather.databinding.ActivityMapsBinding;
import com.example.weather.data.db.entity.ForecastData;
import com.example.weather.data.db.entity.ForecastDetail.Coord;
import com.example.weather.ui.Forecast.ForecastActivity;
import com.example.weather.utils.Constant;
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
                viewModel.getWeatherDataByCoord(getIntent());
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