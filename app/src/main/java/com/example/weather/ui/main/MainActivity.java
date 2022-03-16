package com.example.weather.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.utils.SettingManager;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SettingManager settingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        settingManager= new SettingManager(getBaseContext());
        settingsToolbar();
        settingManager.loadLocale();
    }

    private void settingsToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.russian:
                settingManager.setLocale("ru");
                recreate();
                break;
            case R.id.english:
                 settingManager.setLocale("en");
                recreate();
                break;
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return true;
    }
}