package com.example.weather.ui.temp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.weather.R;
import com.example.weather.databinding.FragmentTempBinding;

public class TempFragment extends Fragment {

    private FragmentTempBinding binding;
    private TempViewModel viewModel;


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
        int id= getArguments().getInt("id",0);
        viewModel.bindDailyWeather(getActivity(), id, binding.dailyIcon, binding.tvMorningValue, binding.tvDayValue,  binding.tvEveValue, binding.tvNightValue);


        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    viewModel.deleteWeatherById(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Navigation.findNavController(view).navigateUp();
            }
        });

    }
}