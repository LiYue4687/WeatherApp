package com.example.weatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.weatherapp.data.room.entity.CityEntity
import com.example.weatherapp.data.room.entity.CityInfoEntity
import com.example.weatherapp.data.room.entity.CityListEntity

@Dao
interface CitySearchDAO {
    @Query(
        """
            SELECT  city_list.ad_code as ad_code, city_list.name as name, city_info.city as city, city_info.province as province 
            FROM    city_list, city_info
            WHERE   ((city_list.name LIKE '%'||:name||'%') OR (city_info.city LIKE '%'||:name||'%'))
                    AND city_list.city_code = city_info.city_code
            """
    )
    fun selectByName(name: String): List<CitySearch>
}

data class CitySearch(
    val ad_code: String,
    val name: String,
    val city: String,
    val province: String,
)