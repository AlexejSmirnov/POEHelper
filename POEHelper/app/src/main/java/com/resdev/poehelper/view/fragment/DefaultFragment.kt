package com.resdev.poehelper.view.fragment

import android.os.Bundle
import android.widget.EdgeEffect
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.view.adapter.MyItemDecoration
import com.resdev.poehelper.view.adapter.callbacks.SwipeCurrencyCallback
import kotlinx.android.synthetic.main.default_fragment.*

abstract class DefaultFragment : Fragment(), MainFragment {
    abstract var recyclerView: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Config.getObservableLeague().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            notifyLeagueChanged()
        })
        Config.getObservableColor().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            paintRecycler()
        })
        Config.getObservableCurrency().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            notifyCurrencyChanged()
        })
    }

    override fun paintRecycler() {
        recyclerView.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                return EdgeEffect(view.context).apply { color = Config.getColor() }
            }
        }
    }

    fun setUpRecyclerView(callback: ItemTouchHelper.SimpleCallback){
        recyclerView = fragmentRecyclerView
        recyclerView.addItemDecoration(
            MyItemDecoration(15)
        )
        recyclerView.setHasFixedSize(false)
        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        paintRecycler()
    }
}