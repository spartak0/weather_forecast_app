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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.databinding.FragmentTempBinding;
import com.example.weather.utils.Constant;

import java.util.ArrayList;

import kotlin.Triple;

public class TempFragment extends Fragment {

    private FragmentTempBinding binding;
    private TempViewModel viewModel;
    private int id;
    TempItemAdapter adapter;

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
        viewModel.fetchHourlyWeather(id);
        viewModel.fetchCurrentWeather(id);

        viewModel.getHourlyLiveData().observe(this, list->{
            setAdapter(list);
        });

        viewModel.getCurrentWeather().observe(this, pair -> {
            binding.tvCurrentTemp.setText(pair.getFirst()+"°");
            Glide.with(getContext())
                    .load(Constant.PREFIX_URL_ICON +
                            pair.getSecond() +
                            Constant.POSTFIX_URL_ICON)
                    .into(binding.ivCurrentTemp);
        });

//        binding.cbSecondDayForecast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewModel.revertIsSecondDailyForecast();
//            }
//        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteWeatherById(id);
                Navigation.findNavController(view).navigateUp();
            }
        });
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        viewModel.update();
//    }

    void setAdapter(ArrayList<Triple<String,String,String>> list){
        TempItemAdapter adapter = new TempItemAdapter(getContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

}


