package com.example.weather.ui.main;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.weather.data.RepositoryImpl;

public class MainViewModel extends ViewModel {

    void setLocale(Context context, String lang){
        RepositoryImpl.getInstance().setLocale(context, lang);
    }

    void loadLocale(Context context){
        RepositoryImpl.getInstance().loadLocale(context);
    }
    Boolean isNetworkAvailable(Context context){
        return RepositoryImpl.getInstance().isNetworkAvailable(context);
    }
}
