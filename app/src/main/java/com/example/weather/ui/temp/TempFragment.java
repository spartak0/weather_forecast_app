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

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.databinding.FragmentTempBinding;
import com.example.weather.utils.Constant;

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
        viewModel.fetchDailyWeather(id);
        viewModel.getLiveData().observe(this,forecast -> {
            binding.tvMorningValue.setText(forecast.get(Constant.MORN));
            binding.tvDayValue.setText(forecast.get(Constant.DAY));
            binding.tvEveValue.setText(forecast.get(Constant.EVE));
            binding.tvNightValue .setText(forecast.get(Constant.NIGHT));
            Glide.with(getContext())
                    .load(Constant.PREFIX_URL_ICON +
                            forecast.get(Constant.DAILY_ICON)+
                            Constant.POSTFIX_URL_ICON)
                    .into(binding.dailyIcon);
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteWeatherById(id).subscribe(()->{}, Throwable::printStackTrace);
                Navigation.findNavController(view).navigateUp();
            }
        });
    }
}