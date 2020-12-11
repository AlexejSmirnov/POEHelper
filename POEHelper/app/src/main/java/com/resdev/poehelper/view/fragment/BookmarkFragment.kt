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
import com.resdev.poehelper.view.adapter.ItemAdapter
import com.resdev.poehelper.view.adapter.callbacks.SwipeBookmarkCallback
import com.resdev.poehelper.viewmodel.BookmarkViewModelFactory
import com.resdev.poehelper.viewmodel.BookmarksViewModel
import javax.inject.Inject
import javax.inject.Named

class BookmarkFragment : DefaultFragment() {
    @Inject
    lateinit var viewModelFactory: BookmarkViewModelFactory
    private lateinit var viewModel: BookmarksViewModel
    private lateinit var itemsAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.default_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookmarksViewModel::class.java)
        setUpRecyclerView(SwipeBookmarkCallback(config))
        itemsAdapter =  ItemAdapter()
        recyclerView.adapter = itemsAdapter
        viewModel.getItems().observe(viewLifecycleOwner, Observer {
            itemsAdapter.submitList(it)
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

    override fun onDestroy() {
        itemsAdapter.closeWindow()
        super.onDestroy()
    }
}