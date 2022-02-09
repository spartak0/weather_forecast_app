package com.example.weather.domain.model.Forecast;

import com.example.weather.domain.model.Forecast.WeatherDetail.Coord;
import com.example.weather.domain.model.Forecast.WeatherDetail.Temp;

public class WeatherData {
    private Coord coord;
    private Temp temp;
    private String name;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Temp getMain() {
        return temp;
    }

    public void setMain(Temp temp) {
        this.temp = temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
