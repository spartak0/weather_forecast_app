package com.example.weather.ui.forecasts.screen_slide_forecast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.example.weather.ui.forecasts.favorite_forecast.FavoriteForecastFragment;
import com.example.weather.ui.forecasts.all_forecast.AllForecastFragment;


public class SwitchFragmentForecastAdapter extends FragmentStateAdapter {


    public SwitchFragmentForecastAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public SwitchFragmentForecastAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public SwitchFragmentForecastAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==1) return new FavoriteForecastFragment();
        else return new AllForecastFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
