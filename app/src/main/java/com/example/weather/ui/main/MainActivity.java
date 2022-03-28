package com.example.weather.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.utils.SettingManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

        if(!settingManager.isNetworkAvailable()){
            MyAlertDialog();
        }
    }

    private void MyAlertDialog(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setNegativeButton("ok", (dialogInterface, i) -> finish());
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