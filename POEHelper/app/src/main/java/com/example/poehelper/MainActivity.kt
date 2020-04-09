package com.example.poehelper

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.annotation.ColorInt
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.poehelper.Fragment.DefaultFragment
import kotlinx.android.synthetic.main.activity_main.*
import yuku.ambilwarna.AmbilWarnaDialog
import java.lang.Exception


class MainActivity : AppCompatActivity() {

val APP_PREFERENCES = "mysettings"
private var mSettings: SharedPreferences? = null
lateinit var fragment:DefaultFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        paintInterface(Config.color)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        var bundle = Bundle()
        bundle.putString("Value", "Currency")
        fragment = DefaultFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()


        navigation_view.setNavigationItemSelectedListener {

            var bundle = Bundle()
            var itemText = navigation_view.menu.findItem(it.itemId).title.toString()
            bundle.putString("Value", itemText)
            fragment = DefaultFragment()
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
            drawer_layout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.main_menu, menu)
        var searchItem = menu?.findItem(R.id.app_bar_search)
        var searchView = searchItem!!.actionView as SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return try{
                    fragment.getAdapter().filter(query)
                    true
                } catch (ex : Exception){
                    false
                }



            }

            override fun onQueryTextChange(newText: String): Boolean {
                return try{
                    fragment.getAdapter().filter(newText)
                    true
                } catch (ex : Exception){
                    false
                }

            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.getItemId()) {
            R.id.currency_picker -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Pick a currency")
                builder.setItems(CurrentValue.getArray()
                ) { dialog, which ->
                    val lv: ListView =
                        (dialog as AlertDialog).listView
                    val text = lv.getItemAtPosition(which).toString()
                    Config.currency = text
                    CurrentValue.getActualData(text)
                    fragment.updateFragment()
                }
                builder.show()

                true
            }
            R.id.league_picker -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Pick a league")
                val leagues = arrayOf(
                    resources.getString(R.string.challenge),
                    resources.getString(R.string.challengehc),
                    resources.getString(R.string.standard),
                    resources.getString(R.string.hardcore))

                builder.setItems(leagues
                ) { dialog, which ->
                    val lv: ListView =
                        (dialog as AlertDialog).listView
                    Config.league = lv.getItemAtPosition(which).toString()
                    fragment.updateFragment()

                }
                builder.show()

                true
            }
            R.id.color_picker->{
                val bar = findViewById<Toolbar>(R.id.toolbar)
                val dialog = AmbilWarnaDialog(
                    this,
                    mSettings!!.getInt("Color", -0x16e19d)!!,
                    object : AmbilWarnaDialog.OnAmbilWarnaListener {
                        override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                            Config.color = color
                            this@MainActivity.paintInterface(color)

                        }

                        override fun onCancel(dialog: AmbilWarnaDialog?) {
                        }
                    })
                fragment.updateFragment()

                dialog.show()
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    fun paintInterface(color : Int){
        toolbar.setBackgroundColor(color)
        this.window.statusBarColor = darkenColor(color)
        this.window.colorMode = color
    }
    @ColorInt
    fun darkenColor(@ColorInt color: Int): Int {
        return Color.HSVToColor(FloatArray(3).apply {
            Color.colorToHSV(color, this)
            this[2] *= 0.9f
        })
    }


    fun saveData(){
        val editor: SharedPreferences.Editor = mSettings!!.edit()
        editor.putString("League", Config.league)
        editor.putString("Currency", Config.currency)
        editor.putInt("Color", Config.color)
        editor.apply()
    }

    fun loadData(){
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        Config.league = mSettings!!.getString("League", resources.getString(R.string.challenge))!!
        Config.currency = mSettings!!.getString("Currency", "Chaos Orb")!!
        Config.color = mSettings!!.getInt("Color", -0x16e19d)!!
    }




}
