package com.mmodi.countries.viewmodel

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountryDetailsVM : ViewModel() {
    var name: MutableLiveData<String>? = MutableLiveData()
    var flag = ObservableField<String>("")
    var population = ObservableField<String>("")
    var capital = ObservableField<String>("")


    fun setData(intent: Intent?) {
        flag.set(intent?.getStringExtra("flag"))
        population.set(intent?.getStringExtra("population"))
        capital.set(intent?.getStringExtra("capital"))

        name?.value = intent?.getStringExtra("name")
    }

    val countryNameObserver: MutableLiveData<String>
        get() {
            if (name == null) {
                name = MutableLiveData()
            }
            return name!!
        }

}