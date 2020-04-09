package com.example.poehelper.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EdgeEffect
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poehelper.Adapter.CurrenciesAdapter
import com.example.poehelper.Adapter.ItemsAdapter
import com.example.poehelper.Adapter.MyAdapterInterface
import com.example.poehelper.Config
import com.example.poehelper.CurrentValue
import com.example.poehelper.PoeLoading
import com.example.poehelper.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress


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
                    if (!isInternetAvailable()){
                        withContext(Dispatchers.Main){
                            toast("No internet connection!")
                        }
                        return@launch
                    }
                    if (!CurrentValue.isInitialized()){
                        CurrentValue.loadData()
                    }
                    val data = PoeLoading.loadCurrencies(Config.league, mess)
                    withContext(Dispatchers.Main){
                        if (mess == "Currency"){
                            CurrentValue.getActualData(Config.currency, data)
                        }
                        currenciesAdapter = CurrenciesAdapter(data)
                        recyclerView.setLayoutManager(LinearLayoutManager(activity))
                        recyclerView.adapter = currenciesAdapter


                    }
                }

            }
            else -> {
                viewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
                GlobalScope.launch {
                    if (!isInternetAvailable()){
                        withContext(Dispatchers.Main){
                        toast("No internet connection!")
                        }
                        return@launch
                    }
                    if (!CurrentValue.isInitialized()){
                        CurrentValue.loadData()
                    }
                    val data = PoeLoading.loadItems(Config.league.toString(), mess!!.replace(" ", ""))
                    withContext(Dispatchers.Main){
                        itemsAdapter = ItemsAdapter(mess, data)
                        recyclerView.setLayoutManager(LinearLayoutManager(activity))
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

    fun toast(text:String){
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
    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }

}
