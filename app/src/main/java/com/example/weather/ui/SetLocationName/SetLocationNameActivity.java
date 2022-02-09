package com.example.weather.ui.SetLocationName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.databinding.ActivitySetLocationNameBinding;
import com.example.weather.ui.maps.MapsActivity;
import com.example.weather.utils.Constant;

public class SetLocationNameActivity extends AppCompatActivity {

    private ActivitySetLocationNameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetLocationNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.btnOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra(Constant.locationName, binding.etName.getText().toString());
                        startActivity(intent);
                    }
                }
        );
    }
}