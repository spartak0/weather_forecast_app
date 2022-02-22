package com.example.weather.ui.temp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.R;
import com.example.weather.databinding.FragmentTempBinding;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.ui.maps.MapsViewModel;

public class TempFragment extends Fragment {

    private FragmentTempBinding binding;
    private TempViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTempBinding.inflate(inflater, container, false);
        View view= binding.getRoot();
        viewModel = new ViewModelProvider(this).get(TempViewModel.class);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        int id= getArguments().getInt("id",0);
        WeatherData tmp = viewModel.getWeatherById( id);
        viewModel.getDailyWeatherByCoord(tmp.getLan(), tmp.getLon(),binding.tvMorningValue, binding.tvDayValue, binding.tvEveValue, binding.tvNightValue);

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    viewModel.deleteWeather(tmp);
                    Navigation.findNavController(view).navigateUp();
            }
        });

    }
}