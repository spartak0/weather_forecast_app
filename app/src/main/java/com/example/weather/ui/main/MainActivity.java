package com.example.weather.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;
import com.example.weather.data.RepositoryImpl;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.utils.Constant;
import com.example.weather.utils.SettingManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SettingManager settingManager= RepositoryImpl.getInstance().getSettingsMenager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        settingsToolbar();
        settingManager.loadLocale();

        if(!settingManager.isNetworkAvailable()){
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
                settingManager.setLocale(Constant.RU);
                recreate();
                break;
            case R.id.english:
                settingManager.setLocale(Constant.EN);
                recreate();
                break;
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return true;
    }

}