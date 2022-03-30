package com.example.weather.ui.main;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;

public class MainViewModel extends ViewModel {

    void setLocale(String lang){
        RepositoryImpl.getInstance().setLocale(lang);
    }

    void loadLocale(){
        RepositoryImpl.getInstance().loadLocale();
    }
    void initSettingsManager(Context context){
        RepositoryImpl.getInstance().initSettingsManager(context);
    }
    Boolean isNetworkAvailable(){
        return RepositoryImpl.getInstance().isNetworkAvailable();
    }
}
