package com.resdev.poehelper.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EdgeEffect
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.resdev.poehelper.*
import com.resdev.poehelper.Adapter.CurrenciesAdapter
import com.resdev.poehelper.Adapter.ItemsAdapter
import com.resdev.poehelper.Adapter.MyAdapterInterface
import com.resdev.poehelper.Callbacks.SwipeCurrencyLeftCallback
import com.resdev.poehelper.Callbacks.SwipeItemLeftCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DefaultFragment : Fragment() {

    companion object {
        fun newInstance() = DefaultFragment()
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ViewModel
    private lateinit var bundle: Bundle
    private lateinit var currenciesAdapter: CurrenciesAdapter
    private lateinit var itemsAdapter: ItemsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bundle = this.arguments!!
        return inflater.inflate(R.layout.default_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = view!!.findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )


    }

    override fun onStart() {
        super.onStart()
        updateFragment()


    }

    fun updateFragment(){
        var mess = bundle.getString("Value")
        when(mess){
            "Currency", "Fragment" -> {
                viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)

                GlobalScope.launch {
                    if (!Util.isInternetAvailable()){
                        withContext(Dispatchers.Main){
                            toast("No internet connection!")
                        }
                        return@launch
                    }
                    if (!CurrentValue.isInitialized()){
                        CurrentValue.loadData()
                    }
                    val data = PoeNinjaLoading.loadCurrencies(Config.league, mess)
                    withContext(Dispatchers.Main){
                        if (mess == "Currency"){
                            CurrentValue.getActualData(Config.currency, data)
                        }
                        val itemTouchHelper =
                            ItemTouchHelper(SwipeCurrencyLeftCallback())
                        itemTouchHelper.attachToRecyclerView(recyclerView)
                        currenciesAdapter = CurrenciesAdapter(data)
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.adapter = currenciesAdapter


                    }
                }

            }
            else -> {
                viewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
                GlobalScope.launch {
                    if (!Util.isInternetAvailable()){
                        withContext(Dispatchers.Main){
                        toast("No internet connection!")
                        }
                        return@launch
                    }
                    if (!CurrentValue.isInitialized()){
                        CurrentValue.loadData()
                    }
                    val data = PoeNinjaLoading.loadItems(Config.league.toString(), mess!!.replace(" ", ""))
                    withContext(Dispatchers.Main){
                        val itemTouchHelper =
                            ItemTouchHelper(SwipeItemLeftCallback())
                        itemTouchHelper.attachToRecyclerView(recyclerView)
                        itemsAdapter = ItemsAdapter(mess, data)

                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.adapter = itemsAdapter

                    }
                }
            }
        }
        recyclerView.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                return EdgeEffect(view.context).apply { setColor(Config.color) }
            }
        }
    }

    private fun toast(text:String){
        Toast.makeText(this.context, text, Toast.LENGTH_LONG).show()
    }

    fun getAdapter(): MyAdapterInterface {
        var mess = bundle.getString("Value")
        return when(mess){
            "Currency", "Fragment" -> {
                currenciesAdapter
            }
            else -> {
                itemsAdapter
            }
        }
    }


}
