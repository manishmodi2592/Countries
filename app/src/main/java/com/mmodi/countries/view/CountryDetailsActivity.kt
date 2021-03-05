package com.mmodi.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mmodi.countries.R
import com.mmodi.countries.databinding.ActivityCountryDetailsBinding
import com.mmodi.countries.viewmodel.CountryDetailsVM

class CountryDetailsActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityCountryDetailsBinding
    private lateinit var viewModel: CountryDetailsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_country_details)
        viewModel = ViewModelProvider(this).get(CountryDetailsVM::class.java)
        viewModel.setData(intent)
        dataBinding.viewModel = viewModel

        setObservers()
    }

    private fun setObservers() {
        viewModel.countryNameObserver.observe(this, Observer {
            supportActionBar?.title = it
        })
    }
}