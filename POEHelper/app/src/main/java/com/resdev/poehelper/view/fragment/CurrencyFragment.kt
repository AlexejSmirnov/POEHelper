package com.resdev.poehelper.view.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.R
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.view.activity.VALUE_KEY
import com.resdev.poehelper.view.adapter.CurrenciesAdapter
import com.resdev.poehelper.view.adapter.callbacks.SwipeCurrencyCallback
import com.resdev.poehelper.view.fragment.util.fromCodeToType
import com.resdev.poehelper.viewmodel.BookmarkViewModelFactory
import com.resdev.poehelper.viewmodel.CurrencyViewModel
import com.resdev.poehelper.viewmodel.CurrencyViewModelFactory
import com.resdev.poehelper.viewmodel.ItemViewModel
import javax.inject.Inject
import javax.inject.Named

class CurrencyFragment : DefaultFragment() {
    @Inject
    lateinit var viewModelFactory: CurrencyViewModelFactory
    private lateinit var viewModel: CurrencyViewModel
    private lateinit var currenciesAdapter : CurrenciesAdapter
    var itemType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemType = fromCodeToType(requireArguments().getInt(VALUE_KEY,-1))
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrencyViewModel::class.java).apply { initializeObserving(itemType) }
        return inflater.inflate(R.layout.default_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView(SwipeCurrencyCallback(config))
        currenciesAdapter = CurrenciesAdapter()
        recyclerView.adapter = currenciesAdapter
        viewModel.getItems().observe(viewLifecycleOwner, Observer {
            currenciesAdapter.submitList(it.lines)

        })
    }

    override fun setFilter(filter: String) {
        viewModel.setFiler(filter)
    }

    override fun notifyCurrencyChanged() {
        currenciesAdapter = CurrenciesAdapter()
        recyclerView.adapter = currenciesAdapter
    }

    override fun onDestroy() {
        currenciesAdapter.closeWindow()
        super.onDestroy()
    }
}