package com.example.weather.data.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.weather.data.db.entity.forecast_detail.Current;
import com.example.weather.data.db.entity.forecast_detail.Daily;

@Entity()
public class WeatherEntity {

    @PrimaryKey(autoGenerate = true)
    private int id=0;

    private float lat;
    private float lon;
    private String name;
    private boolean isFavorite;

    @Ignore
    private Current current;

    @Ignore
    private Daily[] daily;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Daily[] getDaily() {
        return daily;
    }

    public void setDaily(Daily[] daily) {
        this.daily = daily;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
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

    public WeatherEntity(int id, float lat, float lon, String name, boolean isFavorite, Current current, Daily[] daily) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.isFavorite = isFavorite;
        this.current = current;
        this.daily = daily;
    }

    public WeatherEntity() {
    }
}