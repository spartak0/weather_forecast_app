package com.example.weather.domain.model.Forecast;

public class WeatherData {
    private int id;
    private String name;
    private float lan;
    private float lon;
    private float temperature;

    public WeatherData(int id, String name, float lan, float lon, float temperature) {
        this.id = id;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
        this.temperature = temperature;
    }

    public WeatherData(int id, String name, float lan, float lon) {
        this.id = id;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
    }

    public WeatherData(String name, float lan, float lon) {
        this.id = 0;
        this.name = name;
        this.lan = lan;
        this.lon = lon;
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
}
