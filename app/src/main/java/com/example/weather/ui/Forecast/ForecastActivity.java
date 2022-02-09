package com.example.weather.ui.Forecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weather.databinding.ActivityForecastBinding;
import com.example.weather.ui.SetLocationName.SetLocationNameActivity;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity {

    ActivityForecastBinding binding;
    ArrayList<String> aboba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForecastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      //  aboba= new ArrayList<>();
       // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //aboba.add("aboba");
               // setAdapter(view,aboba);
                Intent intent = new Intent(getApplicationContext(), SetLocationNameActivity.class);
                startActivity(intent);
            }
        });

        //todo подписаться на лайвдату
    }
    private void setAdapter(View view, List<String> list){
        ForecastItemAdapter adapter=new ForecastItemAdapter(list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(adapter);
    }

}