package com.sample.weatherforecast.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException
import kotlin.collections.HashMap

class AppUtils {
    companion object {
        fun pojoToMap(obj: Any?): MutableMap<String, String> {
            var map: HashMap<String, String>? = HashMap()
            try {
                val gson = GsonBuilder().create()
                val json = gson.toJson(obj)
                map = Gson().fromJson<HashMap<String, String>>(json, HashMap::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return map ?: HashMap()
        }

    }
}