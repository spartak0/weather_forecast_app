package com.example.weather.data.db.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weather.data.db.dao.WeatherDao;
import com.example.weather.data.db.entity.WeatherEntity;

@Database(entities = WeatherEntity.class, version = 9)
public abstract class WeatherDatabase extends RoomDatabase {
    static WeatherDatabase instance;

    public abstract WeatherDao weatherDao();

    public static WeatherDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, WeatherDatabase.class, "database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
