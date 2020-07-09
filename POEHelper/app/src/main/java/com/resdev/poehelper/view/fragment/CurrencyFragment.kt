package com.resdev.poehelper.view.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EdgeEffect
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.view.adapter.CurrenciesAdapter
import com.resdev.poehelper.view.adapter.MyItemDecoration
import com.resdev.poehelper.view.adapter.callbacks.SwipeCurrencyCallback
import com.resdev.poehelper.viewmodel.CurrencyViewModel
import com.resdev.poehelper.viewmodel.CurrencyViewModelFactory
import kotlinx.android.synthetic.main.default_fragment.*

class CurrencyFragment : Fragment(), MainFragment {

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var bundle: Bundle
    private lateinit var currenciesAdapter : CurrenciesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bundle = this.arguments!!
        viewModel = ViewModelProvider(this, CurrencyViewModelFactory(Application(),
            bundle.getString("Value", bundle.getString("Value", "Currency")))).get(CurrencyViewModel::class.java)
        viewModel.setCurrency()
        viewModel.loadCurrencies()

        return inflater.inflate(R.layout.default_fragment, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        currenciesAdapter = CurrenciesAdapter()
        recyclerView.adapter = currenciesAdapter
        viewModel.getItems().observe(viewLifecycleOwner, Observer {
            currenciesAdapter.submitList(it.lines)

        })
        CurrentValue
    }
    override fun setFilter(filter: String) {
        viewModel.setFiler(filter)
    }

    override fun notifyCurrencyChanged() {
        currenciesAdapter = CurrenciesAdapter()
        recyclerView.adapter = currenciesAdapter
        viewModel.loadCurrencies()
    }

    override fun notifyLeagueChanged() {

        viewModel.loadCurrencies()

    }

    override fun paintRecycler() {
        recyclerView.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                return EdgeEffect(view.context).apply { color = Config.color }
            }
        }
    }

    fun setUpRecyclerView(){
        recyclerView.addItemDecoration(
            MyItemDecoration(15)
        )
        recyclerView.setHasFixedSize(false)
        ItemTouchHelper(SwipeCurrencyCallback()).attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        paintRecycler()
    }

    override fun onDestroy() {
        currenciesAdapter.closeWindow()
        super.onDestroy()
    }
}