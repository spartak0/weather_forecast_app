package com.example.weather.ui.Forecast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.weather.data.db.database.WeatherDatabase;
import com.example.weather.data.db.entity.WeatherEntity;
import com.example.weather.databinding.ActivityForecastBinding;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.ui.SetLocationName.SetLocationNameActivity;
import com.example.weather.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForecastActivity extends AppCompatActivity {

    ActivityForecastBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForecastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    @Override
    protected void onStart() {
        super.onStart();

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetLocationNameActivity.class);
                startActivity(intent);
            }
        });


        ForecastViewModel viewModel = new ViewModelProvider(this).get(ForecastViewModel.class);
        viewModel.getLiveData().observe(this, new Observer<List<WeatherData>>() {
            @Override
            public void onChanged(List<WeatherData> weatherData) {
                setAdapter(binding.recycler,weatherData);
            }
        });
    }
    private void setAdapter(View view, List<WeatherData> list){
        ForecastItemAdapter adapter=new ForecastItemAdapter(list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);
    }

}