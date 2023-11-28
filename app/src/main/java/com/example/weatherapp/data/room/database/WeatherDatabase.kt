package com.example.weatherapp.data.room.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.data.room.dao.CityInfoDAO
import com.example.weatherapp.data.room.dao.CityListDAO
import com.example.weatherapp.data.room.dao.WeatherDAO
import com.example.weatherapp.data.room.entity.CityEntity
import com.example.weatherapp.data.room.entity.CityInfoEntity
import com.example.weatherapp.data.room.entity.CityListEntity
import com.example.weatherapp.data.room.entity.ForecastEntity
import com.example.weatherapp.data.room.util.DataBaseInitCallBack

@Database(
    entities = [
        CityEntity::class, ForecastEntity::class,
        CityListEntity::class, CityInfoEntity::class
               ],
    version = 1,
    exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO
    abstract fun cityListDAO(): CityListDAO
    abstract fun cityInfoDAO(): CityInfoDAO

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database.db"
                )
                    // Don't want to provide migrations
                    // and clear the database when upgrading the version
                    .fallbackToDestructiveMigration()
                    .addCallback(DataBaseInitCallBack(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}