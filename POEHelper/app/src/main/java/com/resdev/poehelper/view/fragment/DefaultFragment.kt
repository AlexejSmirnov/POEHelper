package com.resdev.poehelper.view.fragment

import android.os.Bundle
import android.widget.EdgeEffect
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.view.adapter.MyItemDecoration
import kotlinx.android.synthetic.main.default_fragment.*
import javax.inject.Inject

abstract class DefaultFragment : Fragment(), MainFragment {
    abstract var recyclerView: RecyclerView
    @Inject
    lateinit var config: Config
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        config.getObservableColor().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            paintRecycler()
        })
        config.getObservableCurrency().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            notifyCurrencyChanged()
        })
    }

    override fun paintRecycler() {
        recyclerView.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                return EdgeEffect(view.context).apply { color = config.getColor() }
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