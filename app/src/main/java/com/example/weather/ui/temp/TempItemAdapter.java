package com.example.weather.ui.temp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weather.databinding.ItemForecastBinding;
import com.example.weather.databinding.ItemTempBinding;
import com.example.weather.ui.forecasts.ForecastItemAdapter;
import com.example.weather.utils.Constant;

import java.util.ArrayList;

import kotlin.Triple;

public class TempItemAdapter extends RecyclerView.Adapter<TempItemAdapter.MyViewHolder>{
    ArrayList<Triple<String,String,String>> list;
    Context context;

    public TempItemAdapter(Context context, ArrayList<Triple<String, String, String>> list) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public TempItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new TempItemAdapter.MyViewHolder(ItemTempBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TempItemAdapter.MyViewHolder holder, int position) {
            holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ItemTempBinding binding;

        public MyViewHolder(ItemTempBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        void bind(Triple<String,String,String> triple){
            this.binding.tvDate.setText(triple.getFirst());
            this.binding.tvTemp.setText(triple.getSecond());
            Glide.with(context)
                    .load(Constant.PREFIX_URL_ICON +
                            triple.getThird()+
                            Constant.POSTFIX_URL_ICON)
                    .into(this.binding.imageView);
        }
    }
}
