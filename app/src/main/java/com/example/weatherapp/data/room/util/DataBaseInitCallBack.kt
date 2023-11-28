package com.example.weatherapp.data.room.util

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherapp.data.room.dao.CityInfoDAO
import com.example.weatherapp.data.room.dao.CityListDAO
import com.example.weatherapp.data.room.database.WeatherDatabase
import com.example.weatherapp.data.room.entity.CityInfoEntity
import com.example.weatherapp.data.room.entity.CityListEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class DataBaseInitCallBack(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.i("myTest","the fun is onCreate")
        CoroutineScope(Dispatchers.IO).launch {
            prePopulateCitys(context)
        }
    }

    suspend fun prePopulateCitys(context: Context) {
        Log.i("myTest","the fun is invoked")
        try {
            val cityListDAO: CityListDAO = WeatherDatabase.getDatabase(context).cityListDAO()
            val cityInfoDAO: CityInfoDAO = WeatherDatabase.getDatabase(context).cityInfoDAO()

            val cityList: JSONArray =
                context.resources.assets.open("cityTotalList.json").bufferedReader().use {
                    JSONArray(it.readText())
                }

            var privince: String = "unknown"
            var city: String = "unknown"
            var name: String = "unknown"
            var citycode: String = "unknown"
            var adcode: String = "unknown"
            Log.i("myTest",cityList.toString())
            cityList.takeIf { it.length() > 0 }?.let { list ->
                for (index in 0 until list.length()) {
                    val cityObj = list.getJSONObject(index)
                    Log.i("myTest",cityObj.toString())
                    name = cityObj.get("name")?.toString()?:"unknown"
                    adcode = cityObj.get("adcode")?.toString()?:"unknown"
                    Log.i("myTest",cityObj.has("citycode").toString())
                    if (!cityObj.has("citycode")){
                        privince = name
                    }
                    else {
                        Log.i("myTest",name)
                        Log.i("myTest",adcode)
                        Log.i("myTest",CityInfoEntity(privince, city, citycode).toString())
                        if (citycode!=cityObj.get("citycode")?.toString()?:"unknown"){
                            citycode=cityObj.get("citycode")?.toString()?:"unknown"
                            city = name
                            cityInfoDAO.insert(CityInfoEntity(privince, city, citycode))
                            cityListDAO.insert(CityListEntity(name, citycode, adcode))
                        }
                        else{
                            cityListDAO.insert(CityListEntity(name, citycode, adcode))
                        }

                    }
                }
            }


        } catch (exception: Exception) {
        }
    }
}