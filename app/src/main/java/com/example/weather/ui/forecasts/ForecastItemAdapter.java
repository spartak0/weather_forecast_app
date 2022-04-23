package com.example.weather.ui.forecasts;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.databinding.ItemForecastBinding;
import com.example.weather.domain.model.forecast.WeatherData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.MyViewHolder> {
    private final List<WeatherData> forecasts;
    IsFavoriteClick isFavoriteClick;

    public ForecastItemAdapter(IsFavoriteClick isFavoriteClick) {
        this.forecasts = new ArrayList<>();
        this.isFavoriteClick=isFavoriteClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(forecasts.get(position),position);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<WeatherData> list){
        forecasts.clear();
        forecasts.addAll(list);
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public ItemForecastBinding binding;

        public MyViewHolder(ItemForecastBinding itemForecastBinding) {
            super(itemForecastBinding.getRoot());
            this.binding = itemForecastBinding;
        }


        @SuppressLint({"CheckResult", "SetTextI18n"})
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(WeatherData weatherData, int position) {
            binding.tvCity.setText(weatherData.getName());
            binding.clickable.setOnClickListener(new WeatherItemClickListener(weatherData.getId()));
            binding.tvCurrentValue.setText(Integer.toString(Math.round(weatherData.getCurrentTemp())));
            setCurrentCheck(weatherData);
            binding.myToggleButton.setOnClickListener(view -> {
                weatherData.setFavorite(!weatherData.isFavorite());
                setCurrentCheck(weatherData);
                isFavoriteClick.OnClick(weatherData);
            });

        }

        void setCurrentCheck(WeatherData weatherData){
            binding.myToggleButton.setChecked(weatherData.isFavorite());
            if (binding.myToggleButton.isChecked())
                binding.myToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(binding.getRoot().getContext().getApplicationContext(), R.drawable.ic_baseline_favorite_24));
            else
                binding.myToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(binding.getRoot().getContext().getApplicationContext(),R.drawable.ic_baseline_favorite_border_24));
        }

    }


}

