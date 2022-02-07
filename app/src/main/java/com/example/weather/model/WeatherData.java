package com.example.weather.model;

import com.example.weather.model.WeatherDetail.Coord;
import com.example.weather.model.WeatherDetail.Main;

public class WeatherData {
    private Coord coord;
    private Main main;
    private String name;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
