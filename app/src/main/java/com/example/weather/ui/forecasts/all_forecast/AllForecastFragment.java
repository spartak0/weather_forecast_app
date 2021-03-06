package com.example.weather.ui.forecasts.all_forecast;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.databinding.FragmentForecastBinding;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.ui.forecasts.ForecastItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllForecastFragment extends Fragment {

    FragmentForecastBinding binding;
    AllForecastViewModel viewModel;
    ForecastItemAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getActivity().setTitle(R.string.location);
        viewModel = new ViewModelProvider(this).get(AllForecastViewModel.class);
        adapter = new ForecastItemAdapter(weatherData -> viewModel.update(weatherData));
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);
        if(viewModel.isNetworkAvailable()) viewModel.fetchAllSavedWeather();
        else viewModel.fetchAllSavedWeatherNotNetwork();
        viewModel.getLiveData().observe(this, integerWeatherDataMap -> {
            List<WeatherData> list = new ArrayList<>(integerWeatherDataMap.values());
            adapter.update(list);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onPause() {
        super.onPause();
        viewModel.updateAll();
    }
}