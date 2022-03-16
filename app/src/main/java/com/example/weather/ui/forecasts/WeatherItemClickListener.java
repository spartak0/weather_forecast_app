package com.example.weather.ui.forecasts;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.weather.R;

class WeatherItemClickListener implements View.OnClickListener
{
    int id;

    public WeatherItemClickListener(int id) {
        this.id = id;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        Navigation.findNavController(view).navigate(R.id.action_to_tempActivity, bundle);
    }
}
