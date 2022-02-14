package com.example.weather.data.db.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.weather.data.db.entity.ForecastDetail.Coord;
import com.example.weather.data.db.entity.ForecastDetail.Main;

@Entity
public class WeatherEntity {

    @PrimaryKey(autoGenerate = true)
    private int id=0;

    @Embedded
    private Coord coord;

    @Embedded
    private Main main;

    private String name;

    public WeatherEntity(int id, Coord coord, Main main, String name) {
        this.id = id;
        this.coord = coord;
        this.main = main;
        this.name = name;
    }

    public WeatherEntity(String name, Double lat, Double lon) {
        this.name=name;
        coord=new Coord(lat,lon);
        //coord.setLat(lat);
        //coord.setLon(lon);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

}
