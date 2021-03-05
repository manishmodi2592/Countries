package com.mmodi.countries.retrofit

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.mmodi.countries.utils.GSONUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class GenericAPIRespository(val api : String, val requestObj : Objects, response : Class<T>, )
class GenericAPIHelper {
    fun getData(
        context: Context,
        url: String,
        c: Class<*>,
        apiResponse: ApiResponse
    ) {
        val call: Call<ResponseBody> = RetrofitClient.apiInterface(context).getCountries(
            url
        )

        call.enqueue(object : Callback<ResponseBody?> {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                apiResponse.onSuccess(GSONUtil.fromJson(response.body()?.string(), c))
            }

            override fun onFailure(
                call: Call<ResponseBody?>,
                t: Throwable
            ) {
                apiResponse.onFailure(t)
            }
        })
    }
}