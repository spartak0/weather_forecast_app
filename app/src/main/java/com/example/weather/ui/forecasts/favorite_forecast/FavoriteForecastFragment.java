package com.example.weather.ui.forecasts.favorite_forecast;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.databinding.FragmentFavoriteForecastBinding;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.ui.forecasts.ForecastItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteForecastFragment extends Fragment {

    FragmentFavoriteForecastBinding binding;
    FavoriteForecastViewModel viewModel;
    private ForecastItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteForecastBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart() {
        super.onStart();
        viewModel = new ViewModelProvider(this).get(FavoriteForecastViewModel.class);
        adapter = new ForecastItemAdapter(weatherData -> viewModel.update(weatherData));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);

        viewModel.fetchFavoriteSavedWeather();
        viewModel.getLiveData().observe(this, integerWeatherDataMap -> {
            List<WeatherData> list = new ArrayList<>(integerWeatherDataMap.values());
            adapter.update(list);
        });
    }

}