package com.example.weather.ui.forecast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.R;
import com.example.weather.databinding.FragmentForecastBinding;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.ui.main.MainActivity;
import com.example.weather.ui.setLocationName.SetLocationNameFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ForecastFragment extends Fragment {

    FragmentForecastBinding binding;
    ForecastViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getActivity().setTitle(R.string.location);
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        viewModel = new ViewModelProvider(this).get(ForecastViewModel.class);

        binding.addBtn.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_forecastFragment_to_setLocationNameFragment));

        ForecastItemAdapter adapter = new ForecastItemAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);
        viewModel.getLiveData().observe(this, new Observer<Map<Integer, WeatherData>>() {
                    @Override
                    public void onChanged(Map<Integer, WeatherData> integerWeatherDataMap) {
                        List<WeatherData> list = new ArrayList(integerWeatherDataMap.values());
                        adapter.update(list);
                    }
                });
        viewModel.fetchAllSavedWeather();
    }
}