package com.example.weather.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.weather.R;
import com.example.weather.databinding.ItemForecastBinding;
import com.example.weather.ui.setLocationName.SetLocationNameFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public static void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = fragment.getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,SetLocationNameFragment.class,null);
        fragmentTransaction.commit();
    }
}