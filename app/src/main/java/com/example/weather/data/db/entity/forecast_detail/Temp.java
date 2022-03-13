package com.example.weather.data.db.entity.forecast_detail;

public class Temp {
    float day;
    float night;
    float eve;
    float morn;

    public Temp(float day, float night, float eve, float morn) {
        this.day = day;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }



    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getEve() {
        return eve;
    }

    public void setEve(float eve) {
        this.eve = eve;
    }

    public float getMorn() {
        return morn;
    }

    public void setMorn(float morn) {
        this.morn = morn;
    }
}
