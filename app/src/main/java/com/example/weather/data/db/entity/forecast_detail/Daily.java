package com.example.weather.data.db.entity.forecast_detail;

public class Daily {
    Temp temp;
    Weather[] weather;

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }
}

