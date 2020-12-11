package com.resdev.poehelper.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.resdev.poehelper.R
import com.resdev.poehelper.utils.fromRetrofitItemToRoomEntityList
import com.resdev.poehelper.view.activity.VALUE_KEY
import com.resdev.poehelper.view.adapter.ItemAdapter
import com.resdev.poehelper.view.adapter.callbacks.SwipeItemCallback
import com.resdev.poehelper.view.fragment.util.fromCodeToType
import com.resdev.poehelper.viewmodel.ItemViewModel
import com.resdev.poehelper.viewmodel.ItemViewModelFactory
import javax.inject.Inject

class ItemFragment : DefaultFragment() {
    @Inject
    lateinit var factory: ItemViewModelFactory
    private lateinit var viewModel: ItemViewModel
    private lateinit var itemsAdapter: ItemAdapter
    var itemType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemType = fromCodeToType(requireArguments().getInt(VALUE_KEY,-1))
        viewModel = ViewModelProvider(this, factory).get(ItemViewModel::class.java).apply { initializeObserving(itemType) }
        return inflater.inflate(R.layout.default_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView(SwipeItemCallback(config))
        itemsAdapter =  ItemAdapter()
        recyclerView.adapter = itemsAdapter
        viewModel.getItems().observe(viewLifecycleOwner, Observer {
            itemsAdapter.submitList(
                fromRetrofitItemToRoomEntityList(
                    it.lines,
                    itemType
                )
            )
        })
    }

    override fun setFilter(filter: String) {
        viewModel.setFiler(filter)
    }

    override fun notifyCurrencyChanged() {
        itemsAdapter = ItemAdapter()
        recyclerView.adapter = itemsAdapter
    }

    override fun onDestroy() {
        itemsAdapter.closeWindow()
        super.onDestroy()
    }
}