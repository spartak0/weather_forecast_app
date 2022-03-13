package com.example.weather.ui.forecasts.favoriteForecast;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.databinding.FragmentFavoriteForecastBinding;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.ui.forecasts.ForecastItemAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

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
        System.out.println("ON START");
        viewModel = new ViewModelProvider(this).get(FavoriteForecastViewModel.class);
        adapter = new ForecastItemAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);

        viewModel.getLiveData().observe(this, new Observer<Map<Integer, WeatherData>>() {
            @Override
            public void onChanged(Map<Integer, WeatherData> integerWeatherDataMap) {
                List<WeatherData> list = new ArrayList<>(integerWeatherDataMap.values());
                adapter.update(list);
            }
        });
        viewModel.fetchFavoriteSavedWeather();
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onPause() {
        super.onPause();
        List<WeatherData>  list= new ArrayList<>(adapter.getUpdateMap().values());
        if (!list.isEmpty()) {
            viewModel.updateWeatherData(list);
            adapter.clearUpdateList();
        }
    }
}