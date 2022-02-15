package com.example.weather.ui.Temp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.databinding.ActivitySetLocationNameBinding;
import com.example.weather.databinding.ActivityTempBinding;
import com.example.weather.domain.model.Forecast.WeatherData;
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
        WeatherData tmp = viewModel.getWeatherById( getIntent().getIntExtra("id",0));
        viewModel.getWeatherByCoord(tmp.getLan(), tmp.getLon(),binding.tvTemp);

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    viewModel.deleteWeather(tmp);
                    finish();
            }
        });

    }
}