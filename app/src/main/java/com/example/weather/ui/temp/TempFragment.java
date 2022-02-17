package com.example.weather.ui.temp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.databinding.FragmentTempBinding;
import com.example.weather.domain.model.Forecast.WeatherData;

public class TempFragment extends Fragment {

    private FragmentTempBinding binding;
    private TempViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTempBinding.inflate(inflater, container, false);
        View view= binding.getRoot();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        int id= getArguments().getInt("id",0);
        WeatherData tmp = viewModel.getWeatherById( id);
        viewModel.getWeatherByCoord(tmp.getLan(), tmp.getLon(),binding.tvTemp);

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    viewModel.deleteWeather(tmp);
                    getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }
}