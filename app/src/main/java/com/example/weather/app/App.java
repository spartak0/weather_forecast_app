package com.example.weather.app;

import android.app.Application;

import com.example.weather.data.RepositoryImpl;

public class App extends Application {
    @Override
    public void onCreate() {
        RepositoryImpl.init(getApplicationContext());
        super.onCreate();
    }
}
