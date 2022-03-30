package com.example.weather.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Locale;

public class SettingManager {
    Context context;
    public SettingManager(Context context) {
        this.context=context;
    }

    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
        SharedPreferences.Editor editor= context.getSharedPreferences(Constant.SETTINGS, MODE_PRIVATE).edit();
        editor.putString(Constant.MY_LANG, lang);
        editor.apply();
    }
    public void  loadLocale(){
        SharedPreferences preferences = context.getSharedPreferences(Constant.SETTINGS, MODE_PRIVATE);
        String language = preferences.getString(Constant.MY_LANG,"");
        setLocale(language);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) this.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
