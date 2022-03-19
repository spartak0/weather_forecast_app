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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.databinding.FragmentTempBinding;
import com.example.weather.domain.model.forecast.WeatherData;
import com.example.weather.utils.Constant;

import java.util.concurrent.TimeUnit;

public class TempFragment extends Fragment {

    private FragmentTempBinding binding;
    private TempViewModel viewModel;
    private int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTempBinding.inflate(inflater, container, false);
        View view= binding.getRoot();
        viewModel = new ViewModelProvider(this).get(TempViewModel.class);
        getActivity().setTitle(R.string.weather_forecast);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        id = getArguments().getInt("id",0);
        viewModel.fetchWeatherData(id);
        viewModel.getWeatherDataLiveData().observe(this, weatherData ->{
            binding.cbSecondDayForecast.setChecked(weatherData.isSecondDayForecast());
            if (weatherData.isSecondDayForecast()){
                binding.tableLayout2.setVisibility(View.VISIBLE);
                binding.dailyIcon2.setVisibility(View.VISIBLE);
            }
            else{
                binding.tableLayout2.setVisibility(View.GONE);
                binding.dailyIcon2.setVisibility(View.GONE);
            }
        });

        viewModel.fetchDailyWeather(id);
        viewModel.getLiveData().observe(this,forecast -> {
            binding.tvMorningValue.setText(forecast.get(Constant.MORN));
            binding.tvDayValue.setText(forecast.get(Constant.DAY));
            binding.tvEveValue.setText(forecast.get(Constant.EVE));
            binding.tvNightValue.setText(forecast.get(Constant.NIGHT));
            Glide.with(getContext())
                    .load(Constant.PREFIX_URL_ICON +
                            forecast.get(Constant.DAILY_ICON)+
                            Constant.POSTFIX_URL_ICON)
                    .into(binding.dailyIcon);
            binding.tvMorningValue2.setText(forecast.get(Constant.MORN_2));
            binding.tvDayValue2.setText(forecast.get(Constant.DAY_2));
            binding.tvEveValue2.setText(forecast.get(Constant.EVE_2));
            binding.tvNightValue2.setText(forecast.get(Constant.NIGHT_2));
            Glide.with(getContext())
                    .load(Constant.PREFIX_URL_ICON +
                            forecast.get(Constant.DAILY_ICON_2) +
                            Constant.POSTFIX_URL_ICON)
                    .into(binding.dailyIcon2);
        });

        binding.cbSecondDayForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.revertIsSecondDailyForecast();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteWeatherById(id).subscribe(()->{}, Throwable::printStackTrace);
                Navigation.findNavController(view).navigateUp();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.update();
    }
}

