package com.example.weather.ui.temp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.databinding.FragmentTempBinding;
import com.example.weather.domain.model.Forecast.WeatherData;
import com.example.weather.ui.maps.MapsViewModel;
import com.example.weather.utils.Constant;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        WeatherData tmp = viewModel.getWeatherById( id);
        viewModel.getDailyWeatherByCoord(tmp.getLan(), tmp.getLon())
                .subscribe(hashMap -> {
                    binding.tvMorningValue.setText(hashMap.get(Constant.morn));
                        binding.tvDayValue.setText(hashMap.get(Constant.day));
                        binding.tvEveValue.setText(hashMap.get(Constant.eve));
                        binding.tvNightValue .setText(hashMap.get(Constant.night));
                        System.out.println(hashMap.get(Constant.dailyIcon));
                        Glide.with(getActivity())
                                .load(Constant.prefix_url_icon+
                                        hashMap.get(Constant.dailyIcon)+
                                        Constant.postfix_url_icon)
                        .into(binding.dailyIcon);
                });


        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    viewModel.deleteWeather(tmp)
                            .subscribeOn(Schedulers.io())
                            .subscribe(()->{}, Throwable::printStackTrace);
                    Navigation.findNavController(view).navigateUp();
            }
        });

    }
}