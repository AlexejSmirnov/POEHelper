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
import com.resdev.poehelper.R
import com.resdev.poehelper.view.adapter.ItemsAdapter
import com.resdev.poehelper.view.adapter.MyItemDecoration
import com.resdev.poehelper.view.adapter.callbacks.SwipeItemCallback
import com.resdev.poehelper.viewmodel.ItemViewModel
import com.resdev.poehelper.viewmodel.ItemViewModelFactory
import kotlinx.android.synthetic.main.default_fragment.*

class ItemFragment : DefaultFragment() {
    private lateinit var viewModel: ItemViewModel
    override lateinit var recyclerView: RecyclerView
    private lateinit var itemsAdapter: ItemsAdapter
    var itemType = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemType = FragmentUtil.fromCodeToType(requireArguments().getInt("Value",-1))

        viewModel = ViewModelProvider(this, ItemViewModelFactory(Application(),  itemType)).get(ItemViewModel::class.java)
        viewModel.setLastItem()
        viewModel.loadItems()
        return inflater.inflate(R.layout.default_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        itemsAdapter =  ItemsAdapter(itemType)
        recyclerView.adapter = itemsAdapter
        viewModel.getItems().observe(viewLifecycleOwner, Observer {
            itemsAdapter.submitList(it.lines)
        })
    }

    override fun setFilter(filter: String) {
        viewModel.setFiler(filter)
    }

    override fun notifyCurrencyChanged() {
        itemsAdapter = ItemsAdapter(itemType)
        recyclerView.adapter = itemsAdapter
        viewModel.loadItems()
    }

    override fun notifyLeagueChanged() {
        viewModel.loadItems()
    }




    fun setUpRecyclerView(){
        recyclerView = fragmentRecyclerView
        recyclerView.addItemDecoration(
            MyItemDecoration(15)
        )
        ItemTouchHelper(SwipeItemCallback()).attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        paintRecycler()
    }

    override fun onDestroy() {
        itemsAdapter.closeWindow()
        super.onDestroy()
    }
}