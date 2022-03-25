package com.example.weather.domain.model.forecast;

public class WeatherData {
    private int id;
    private String name;
    private float lan;
    private float lon;
    private float temperature;
    private boolean isFavorite;
    private boolean secondDayForecast;

    public WeatherData(int id, String name, float lan, float lon, float temperature, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
        this.temperature = temperature;
        this.isFavorite = isFavorite;
    }

    public WeatherData(int id, String name, float lan, float lon, boolean isFavorite, boolean secondDayForecast) {
        this.id = id;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
        this.isFavorite= isFavorite;
        this.secondDayForecast = secondDayForecast;
    }

    public WeatherData(String name, float lan, float lon,Boolean isFavorite, boolean secondDayForecast) {
        this.id = 0;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
        this.isFavorite= isFavorite;
        this.secondDayForecast= secondDayForecast;

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

    public boolean isSecondDayForecast() {
        return secondDayForecast;
    }

    public void setSecondDayForecast(boolean secondDayForecast) {
        this.secondDayForecast = secondDayForecast;
    }
}
