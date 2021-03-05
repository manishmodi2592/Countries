package com.mmodi.countries.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmodi.countries.R
import com.mmodi.countries.adapter.CounrtyListAdapter
import com.mmodi.countries.databinding.ActivityMainBinding
import com.mmodi.countries.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    private var adapter: CounrtyListAdapter? = null
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(
            MainActivityViewModel::class.java
        )

        initSearhView()
        initAdapter()
        setObserver()
    }

    private fun setObserver() {
        viewModel.fetchCountries(applicationContext).observe(this, Observer {
            adapter?.setDataList(it?.toMutableList() ?: mutableListOf())
        })

        viewModel.sortDataListObserver.observe(this, Observer {
            adapter?.setDataList(it.asList() ?: listOf())
        })

        viewModel.clickedCountry.observe(this, Observer {
            val intent = Intent(this, CountryDetailsActivity::class.java)
            intent.putExtra("flag", it.flag)
            intent.putExtra("name", it.name)
            intent.putExtra("capital", it.capital)
            intent.putExtra("population", it.population.toString())
            startActivity(intent)
        })
    }

    private fun initAdapter() {
        adapter = CounrtyListAdapter(viewModel)
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dataBinding.recyclerView.addItemDecoration(itemDecor)
        dataBinding.recyclerView.adapter = adapter
    }

    fun initSearhView() {
        dataBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText)
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort_a_z -> {
                adapter?.let { viewModel.sortData(true) }
                return true
            }
            R.id.menu_sort_z_a -> {
                adapter?.let { viewModel.sortData(false) }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}