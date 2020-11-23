package com.resdev.poehelper.view.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.R
import com.resdev.poehelper.model.fromRetrofitItemToRoomEntityList
import com.resdev.poehelper.view.adapter.ItemAdapter
import com.resdev.poehelper.view.adapter.callbacks.SwipeItemCallback
import com.resdev.poehelper.view.fragment.util.fromCodeToType
import com.resdev.poehelper.viewmodel.ItemViewModel
import com.resdev.poehelper.viewmodel.ItemViewModelFactory

class ItemFragment : DefaultFragment() {
    private lateinit var viewModel: ItemViewModel
    override lateinit var recyclerView: RecyclerView
    private lateinit var itemsAdapter: ItemAdapter
    var itemType = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemType = fromCodeToType(requireArguments().getInt("Value",-1))

        viewModel = ViewModelProvider(this, ItemViewModelFactory(Application(),  itemType)).get(ItemViewModel::class.java)
        viewModel.setLastItem()
        viewModel.loadItems()
        return inflater.inflate(R.layout.default_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView(SwipeItemCallback())
        itemsAdapter =  ItemAdapter()
        recyclerView.adapter = itemsAdapter
        viewModel.getItems().observe(viewLifecycleOwner, Observer {
            itemsAdapter.submitList(fromRetrofitItemToRoomEntityList(it.lines, itemType))
        })
    }

    override fun setFilter(filter: String) {
        viewModel.setFiler(filter)
    }

    override fun notifyCurrencyChanged() {
        itemsAdapter = ItemAdapter()
        recyclerView.adapter = itemsAdapter
        viewModel.loadItems()
    }

    override fun notifyLeagueChanged() {
        viewModel.loadItems()
    }


    override fun onDestroy() {
        itemsAdapter.closeWindow()
        super.onDestroy()
    }
}