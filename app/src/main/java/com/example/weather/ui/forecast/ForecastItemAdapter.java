package com.example.weather.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.databinding.ItemForecastBinding;
import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.List;

public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.MyViewHolder> {
    private final List<WeatherData> forecasts;

    public ForecastItemAdapter(List<WeatherData> forecast) {
        this.forecasts = forecast;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(forecasts.get(position),position);

    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public ItemForecastBinding binding;

        public MyViewHolder(ItemForecastBinding itemForecastBinding) {
            super(itemForecastBinding.getRoot());
            this.binding = itemForecastBinding;
        }


        public void bind(WeatherData weatherData, int position) {
            this.binding.tvIndex.setText((position+1)+"");
            this.binding.tvCity.setText(weatherData.getName());
            this.binding.layout.setOnClickListener(new onClickListener(weatherData.getId()));

        }

        public class onClickListener implements View.OnClickListener
        {
            int id;

            public onClickListener(int id) {
                this.id = id;
            }

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                Navigation.findNavController(view.getRootView()).navigate(R.id.action_forecastFragment_to_tempActivity, bundle);
            }
        }

    }


}
