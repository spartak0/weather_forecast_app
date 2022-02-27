package com.example.weather.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.databinding.ActivityMapsBinding;
import com.example.weather.databinding.ItemForecastBinding;
import com.example.weather.ui.setLocationName.SetLocationNameFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        settingsToolbar();
        loadLocale();
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
                setLocale("ru");
                recreate();
                break;
            case R.id.english:
                setLocale("en");
                recreate();
                break;
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return true;
    }

    void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor= getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("MyLang", lang);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("MyLang","");
        setLocale(language);
    }


}