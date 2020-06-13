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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.Config
import com.resdev.poehelper.R
import com.resdev.poehelper.view.adapter.BookmarkAdapter
import com.resdev.poehelper.view.adapter.MyItemDecoration
import com.resdev.poehelper.view.adapter.callbacks.SwipeBookmarkCallback
import com.resdev.poehelper.view.adapter.callbacks.SwipeItemCallback
import com.resdev.poehelper.viewmodel.BookmarkViewModelFactory
import com.resdev.poehelper.viewmodel.BookmarksViewModel
import kotlinx.android.synthetic.main.default_fragment.*

class BookmarkFragment : Fragment(), MainFragment {
    private lateinit var viewModel: BookmarksViewModel
    private lateinit var itemsAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, BookmarkViewModelFactory(Application())).get(
            BookmarksViewModel::class.java)
        viewModel.loadItems()
        return inflater.inflate(R.layout.default_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        itemsAdapter =  BookmarkAdapter()
        recyclerView.adapter = itemsAdapter
        viewModel.getItems().observe(viewLifecycleOwner, Observer {
            itemsAdapter.submitList(it)
        })
    }

    override fun setFilter(filter: String) {
        viewModel.setFiler(filter)
    }

    override fun notifyCurrencyChanged() {
        itemsAdapter = BookmarkAdapter()
        recyclerView.adapter = itemsAdapter
        viewModel.loadItems()
    }

    override fun notifyLeagueChanged() {
        viewModel.loadItems()
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
        ItemTouchHelper(SwipeBookmarkCallback()).attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        paintRecycler()
    }

    override fun onDestroy() {
        itemsAdapter.closeWindow()
        super.onDestroy()
    }
}