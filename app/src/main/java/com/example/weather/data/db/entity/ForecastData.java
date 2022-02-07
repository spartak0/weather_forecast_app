package com.example.weather.data.db.entity;

import com.example.weather.data.db.entity.ForecastDetail.Coord;
import com.example.weather.data.db.entity.ForecastDetail.Main;

public class ForecastData {
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
