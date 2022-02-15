package com.example.weather.app;

import android.app.Application;
import android.content.Context;

import com.example.weather.data.RepositoryImpl;

public class App extends Application {
    @Override
    public void onCreate() {
        RepositoryImpl.Init(getApplicationContext());
        super.onCreate();
    }
}
