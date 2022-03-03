package com.example.weather.ui.forecast;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.databinding.ItemForecastBinding;
import com.example.weather.domain.model.Forecast.WeatherData;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.MyViewHolder> {
    private final List<WeatherData> forecasts;
    private final List<Observable<Float>> currentTemp;

    public ForecastItemAdapter(List<WeatherData> forecast, List<Observable<Float>> currentTemp) {
        this.forecasts = forecast;
        this.currentTemp = currentTemp;
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

    @SuppressLint("NotifyDataSetChanged")
    void update(List<WeatherData> list){
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


        @SuppressLint("CheckResult")
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(WeatherData weatherData, int position) {
            this.binding.tvIndex.setText((position+1)+"");
            this.binding.tvCity.setText(weatherData.getName());
            this.binding.layout.setOnClickListener(new onClickListener(weatherData.getId()));
            currentTemp.get(position).subscribe(x -> {
                binding.tvCurrentTemp.setText(""+x);
            });
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
                Navigation.findNavController(view).navigate(R.id.action_forecastFragment_to_tempActivity, bundle);
            }
        }

    }


}
