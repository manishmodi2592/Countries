package com.mmodi.countries.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ApiInterface {

    @GET
    fun getCountries(
        @Url url: String,
    ): Call<ResponseBody>

}