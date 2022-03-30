package com.example.weather.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.R;
import com.example.weather.data.RepositoryImpl;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.utils.Constant;
import com.example.weather.utils.SettingManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        settingsToolbar();
        viewModel= new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadLocale(getBaseContext());
        if(!viewModel.isNetworkAvailable()){
            MyAlertDialog();
        }
    }

    private void MyAlertDialog(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setNegativeButton(R.string.ok_btn, (dialogInterface, i) -> {
                });
        builder.show();
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
                viewModel.setLocale(getBaseContext(), Constant.RU);
                recreate();
                break;
            case R.id.english:
                viewModel.setLocale(getBaseContext() ,Constant.EN);
                recreate();
                break;
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return true;
    }

}