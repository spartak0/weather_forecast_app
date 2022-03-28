package com.example.weather.data.db.entity.forecast_detail;

public class Current {
    float temp;
    Weather[] weather;

    public Current(float temp, Weather[] weather) {
        this.temp = temp;
        this.weather = weather;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }
}
