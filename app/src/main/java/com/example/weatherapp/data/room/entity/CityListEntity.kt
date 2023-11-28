package com.example.weatherapp.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "city_list"
//    ,
//    foreignKeys = [ForeignKey(
//        entity = CityInfoEntity::class,
//        childColumns = ["city_code"],
//        parentColumns = ["city_code"])]
)
class CityListEntity(
    val name: String,
    val city_code: String,
    @PrimaryKey val ad_code: String = ""
) {
}