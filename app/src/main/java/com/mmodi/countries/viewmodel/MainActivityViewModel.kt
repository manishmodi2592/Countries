package com.mmodi.countries.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmodi.countries.model.CountryListModelItem
import com.mmodi.countries.repository.CountryRepository
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel : ViewModel() {


    private var itemClickedLiveData: MutableLiveData<CountryListModelItem>? = null
    private var sortDataListLD: MutableLiveData<Array<CountryListModelItem>>? = null

    val sortDataListObserver: MutableLiveData<Array<CountryListModelItem>>
        get() {
            if (sortDataListLD == null) {
                sortDataListLD = MutableLiveData()
            }
            return sortDataListLD!!
        }

    fun fetchCountries(context : Context): LiveData<Array<CountryListModelItem>?> {
        return CountryRepository.getCountries(context)
    }

    val clickedCountry: MutableLiveData<CountryListModelItem>
        get() {
            if (itemClickedLiveData == null) {
                itemClickedLiveData = MutableLiveData()
            }
            return itemClickedLiveData!!
        }

    fun onItemClick(model: CountryListModelItem) {
        itemClickedLiveData?.value = model
    }

    fun sortData(aToZ: Boolean) {
        viewModelScope.launch {
            sortDataListLD?.postValue(CountryRepository.sortData(aToZ).value)
        }
    }
}