package com.example.weather.ui.Forecast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.databinding.ItemForecastBinding;

import java.util.List;

public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.MyViewHolder> {
    private final List<String> forecasts;

    public ForecastItemAdapter(List<String> forecast){
        this.forecasts = forecast;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvCity.setText("123");
        holder.binding.tvIndex.setText(""+position);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public ItemForecastBinding binding;
        public MyViewHolder(ItemForecastBinding itemForecastBinding) {
            super(itemForecastBinding.getRoot());
            this.binding=itemForecastBinding;
        }
    }

}
