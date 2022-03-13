package com.example.weather.domain.model.Forecast;

import android.text.BoringLayout;

public class WeatherData {
    private int id;
    private String name;
    private float lan;
    private float lon;
    private float temperature;
    private  boolean isFavorite;

    public WeatherData(int id, String name, float lan, float lon, float temperature, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
        this.temperature = temperature;
        this.isFavorite = isFavorite;
    }

    public WeatherData(int id, String name, float lan, float lon, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
        this.isFavorite= isFavorite;
    }

    public WeatherData(String name, float lan, float lon,Boolean isFavorite) {
        this.id = 0;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
        this.isFavorite= isFavorite;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLan() {
        return lan;
    }

    public void setLan(float lan) {
        this.lan = lan;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
