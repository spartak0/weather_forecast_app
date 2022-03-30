package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.databinding.FragmentTempBinding;
import com.example.weather.utils.Constant;
import com.example.weather.utils.SettingManager;

import java.util.ArrayList;

import kotlin.Pair;
import kotlin.Triple;

public class TempFragment extends Fragment {

    private FragmentTempBinding binding;
    private TempViewModel viewModel;
    private int id;
    TempItemAdapter adapter;
    SettingManager settingManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTempBinding.inflate(inflater, container, false);
        View view= binding.getRoot();
        viewModel = new ViewModelProvider(this).get(TempViewModel.class);
        getActivity().setTitle(R.string.weather_forecast);

        adapter = new TempItemAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        id = getArguments().getInt("id",0);
        viewModel.fetchWeatherData(id);

        if(viewModel.isNetworkAvailable(requireActivity().getBaseContext())) {
            viewModel.fetchHourlyWeather(id);
            viewModel.fetchDailyWeather(id);
        }
        else viewModel.fetchNoNetwork(id);

        viewModel.getDailyLiveData().observe(this,pairs -> {
            if (pairs.size()>1) {
                Pair<Float, String> firstDayPair = pairs.get(0);
                Pair<Float, String> secondDayPair = pairs.get(1);

                binding.tvDailyTempValue.setText(Math.round(firstDayPair.getFirst())+"℃");
                Glide.with(getContext())
                        .load(Constant.PREFIX_URL_ICON +
                                firstDayPair.getSecond() +
                                Constant.POSTFIX_URL_ICON)
                        .into(binding.ivDailyTemp);

                binding.tvDailyTemp2Value.setText(Math.round(secondDayPair.getFirst())+"℃");
                Glide.with(getContext())
                        .load(Constant.PREFIX_URL_ICON +
                                secondDayPair.getSecond() +
                                Constant.POSTFIX_URL_ICON)
                        .into(binding.ivDailyTemp2);
            }
        });

        viewModel.getHourlyLiveData().observe(this, list->{
            adapter.update(list);
        });


        viewModel.getWeatherDataLiveData().observe(this,weatherData -> {
            binding.cbSecondDayForecast.setChecked(weatherData.isSecondDayForecast());
            if(binding.cbSecondDayForecast.isChecked()){
                binding.secondDayCard.setVisibility(View.VISIBLE);
            }
            else{
                binding.secondDayCard.setVisibility(View.GONE);
            }
        });
        binding.cbSecondDayForecast.setOnClickListener(view -> viewModel.revertIsSecondDailyForecast());

        binding.btnDelete.setOnClickListener(view -> {
            viewModel.deleteWeatherById(id);
            Navigation.findNavController(view).navigateUp();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.update();
    }
}


