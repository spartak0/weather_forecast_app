package com.example.weather.ui.main;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;

public class MainViewModel extends ViewModel {

    void setLocale(Context context, String lang){
        RepositoryImpl.getInstance().setLocale(lang);
    }

    void loadLocale(Context context){
        RepositoryImpl.getInstance().initSettingsManager(context);
        RepositoryImpl.getInstance().loadLocale();
    }
    Boolean isNetworkAvailable(){
        return RepositoryImpl.getInstance().isNetworkAvailable();
    }
}
