package com.mmodi.countries.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val MainServer = "https://restcountries.eu/"

    val cacheSize = (50 * 1024 * 1024).toLong()

    private fun retrofitClient(context: Context): Retrofit.Builder {

        val myCache = Cache(context.cacheDir, cacheSize)

        /*val logging = HttpLoggingInterceptor()
        logging.setLevel(Level.BODY)*/

        val okhttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
        //.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(MainServer)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    /*val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }*/

    fun apiInterface(context: Context): ApiInterface {
        return retrofitClient(context)
            .build()
            .create(ApiInterface::class.java)
    }
}
