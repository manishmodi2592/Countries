package com.mmodi.countries.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mmodi.countries.model.CountryListModel
import com.mmodi.countries.model.CountryListModelItem
import com.mmodi.countries.retrofit.APIUrls
import com.mmodi.countries.retrofit.ApiResponse
import com.mmodi.countries.retrofit.GenericAPIHelper

@Suppress("UNCHECKED_CAST")
object CountryRepository {

    val mutableLiveData = MutableLiveData<Array<CountryListModelItem>>()

    fun getCountries(context: Context): MutableLiveData<Array<CountryListModelItem>> {
        GenericAPIHelper().getData(
            context,
            APIUrls.countryListAPI,
            Array<CountryListModelItem>::class.java,
            object : ApiResponse {
                override fun <T> onSuccess(response: T) {
                    mutableLiveData.value = response as Array<CountryListModelItem>
                }

                override fun <T> onFailure(error: T) {
                }

            })
        return mutableLiveData
    }

    fun sortData(aToZ: Boolean): MutableLiveData<Array<CountryListModelItem>> {
        val dataList = mutableLiveData.value
        if (aToZ) {
            mutableLiveData.value = dataList?.sortedBy { it.name }?.toTypedArray()
        } else {
            mutableLiveData.value = dataList?.sortedByDescending { it.name }?.toTypedArray()
        }
        return mutableLiveData
    }
}