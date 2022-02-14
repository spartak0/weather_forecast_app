package com.example.weather.ui.Temp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.databinding.ActivitySetLocationNameBinding;
import com.example.weather.databinding.ActivityTempBinding;
import com.example.weather.ui.maps.MapsViewModel;

public class TempActivity extends AppCompatActivity {

    private ActivityTempBinding binding;
    private TempViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(TempViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int id= getIntent().getIntExtra("id",0);
        WeatherEntity tmp = viewModel.getWeatherById( getIntent().getIntExtra("id",0));
        viewModel.getWeatherDataByCoord(tmp.getCoord().getLat(), tmp.getCoord().getLon(),binding.tvTemp);

    }
}