package com.mmodi.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mmodi.countries.BR
import com.mmodi.countries.R
import com.mmodi.countries.databinding.CountryItemLayoutBinding
import com.mmodi.countries.model.CountryListModelItem
import com.mmodi.countries.viewmodel.MainActivityViewModel
import java.util.*
import kotlin.collections.ArrayList

class CounrtyListAdapter(val viewModel: MainActivityViewModel) :
    RecyclerView.Adapter<CounrtyListAdapter.CountryViewHolder>(), Filterable {

    private var filterList: List<CountryListModelItem> = emptyList()
    private var countryList: List<CountryListModelItem> = listOf()


    fun setDataList(countries: List<CountryListModelItem>) {
        this.countryList = countries
        this.filterList = countryList
        notifyDataSetChanged()
    }

    fun getDataList(): List<CountryListModelItem> {
        return countryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CountryItemLayoutBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.country_item_layout,
                parent,
                false
            )
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filterList.size ?: 0
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        filterList.get(position).let { holder.bind(viewModel, it) }
    }

    class CountryViewHolder(val binding: CountryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: MainActivityViewModel, country: CountryListModelItem) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.model, country)
            binding.executePendingBindings()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = countryList as ArrayList<CountryListModelItem>
                } else {
                    val resultList = ArrayList<CountryListModelItem>()
                    for (row in countryList) {
                        if (row.name.toLowerCase(Locale.getDefault())
                                .contains(constraint.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values != null) {
                    filterList = results.values as ArrayList<CountryListModelItem>
                    notifyDataSetChanged()
                }
            }
        }
    }

}