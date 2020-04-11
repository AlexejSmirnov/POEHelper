package com.resdev.poehelper


import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.resdev.poehelper.Fragment.DefaultFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*


class MainActivity : AppCompatActivity() {

val APP_PREFERENCES = "mysettings"
private var mSettings: SharedPreferences? = null
lateinit var fragment:DefaultFragment
lateinit var leagues: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        loadData()
        setLang()
        GlobalScope.launch {
            setupSettings()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paintInterface(Config.color)
        title = Config.league
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            0, 0
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        var bundle = Bundle()
        bundle.putString("Value", "Currency")
        fragment = DefaultFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()

        navigation_view.itemIconTintList = null;
        navigation_view.setNavigationItemSelectedListener {

            var bundle = Bundle()
            var itemText = when (it.itemId){
                R.id.nav_currency->"Currency"
                R.id.nav_fragment->"Fragment"
                R.id.nav_delirium_orb->"Delirium Orb"
                R.id.nav_watchstone->"Watchstone"
                R.id.nav_oil->"Oil"
                R.id.nav_incubator->"Incubator"
                R.id.nav_scarab->"Scarab"
                R.id.nav_fossil->"Fossil"
                R.id.nav_essence->"Essence"
                R.id.nav_resonator->"Resonator"
                R.id.nav_divination_card->"Divination Card"
                R.id.nav_skill_gem->"Skill Gem"
                R.id.nav_base_type->"Base Type"
                R.id.nav_helmet_enchant->"Helmet Enchant"
                R.id.nav_map->"Map"
                R.id.nav_unique_armour->"Unique Armour"
                R.id.nav_unique_flask->"Unique Flask"
                R.id.nav_unique_weapon->"Unique Weapon"
                R.id.nav_unique_accessory->"Unique Accessory"
                R.id.nav_unique_jewel->"Unique Jewel"
                R.id.nav_prophecy->"Prophecy"
                R.id.nav_unique_map->"Unique Map"
                R.id.nav_beast->"Beast"
                else->"Vial"
            }
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
                if (!CurrentValue.isInitialized()){
                    GlobalScope.launch {
                        setupSettings()
                    }
                    Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG).show()
                    return false

                }
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(resources.getString(R.string.choose_currency))
                var arrays = CurrentValue.getArray()
                builder.setItems(arrays[1]
                ) { dialog, which ->
                    val lv: ListView =
                        (dialog as AlertDialog).listView
                    val text = arrays[0][which]
                    Config.currency = text
                    CurrentValue.getActualData(text)
                    fragment.updateFragment()
                }
                builder.show()

                true
            }
            R.id.league_picker -> {
                if (!::leagues.isInitialized){
                    GlobalScope.launch {
                        setupSettings()
                    }
                    Toast.makeText(this, "No internet connection\nor problems with api.pathofexile.com", Toast.LENGTH_LONG).show()
                    return false

                }
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(resources.getString(R.string.choose_league))
                builder.setItems(leagues
                ) { dialog, which ->
                    val lv: ListView =
                        (dialog as AlertDialog).listView
                    Config.league = lv.getItemAtPosition(which).toString()
                    title = Config.league
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
            R.id.language_picker -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(resources.getString(R.string.change_language))
                var languages = arrayOf("en", "pt", "ru", "th", "ge", "fr", "es", "ko")
                val languagesTitle = arrayOf(
                    "English",
                    "Portuguese",
                    "Russian",
                    "Thai",
                    "German",
                    "French",
                    "Spanish",
                    "Korean")
                builder.setItems(languagesTitle
                ) { dialog, which ->
                    Config.language=languages[which]
                    GlobalScope.launch {
                        CurrentValue.loadData()

                        withContext(Dispatchers.Main){
                            fragment.updateFragment()
                            getThis().recreate()
                        }
                    }


                }
                builder.show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("MyLog", "onStop")
        saveData()
    }

    fun paintInterface(color : Int){
        toolbar.setBackgroundColor(color)
        this.window.statusBarColor = darkenColor(color)
        if ((android.os.Build.VERSION.SDK).toInt()>=26){
            this.window.colorMode = color
        }

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
        editor.putString("Language", Config.language)
        editor.putInt("Color", Config.color)
        editor.apply()
    }

    fun loadData(){
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        Config.league = mSettings!!.getString("League", "Standard")!!
        Config.currency = mSettings!!.getString("Currency", "Chaos Orb")!!
        Config.language = mSettings!!.getString("Language", "en")!!
        Config.color = mSettings!!.getInt("Color", -0x16e19d)!!
    }


    fun setupSettings(){
        if (Util.isInternetAvailable()){
            if (!::leagues.isInitialized){
                var loaded  = PoeLoading.loadLeagues()
                leagues= loaded.getEditedLeagues()
            }
            if (!CurrentValue.isInitialized()){
                CurrentValue.loadData()
            }

        }
    }

    fun setLang(){
        val activityRes: Resources = resources
        val activityConf: Configuration = activityRes.getConfiguration()
        var lang = Config.language
        if (lang == "ge"){
            lang = "de"
        }
        val newLocale = Locale(lang)
        activityConf.setLocale(newLocale)
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics())

        val applicationRes: Resources = applicationContext.resources
        val applicationConf: Configuration = applicationRes.getConfiguration()
        applicationConf.setLocale(newLocale)
        applicationRes.updateConfiguration(
            applicationConf,
            applicationRes.getDisplayMetrics()
        )

    }

    fun getThis():MainActivity{
        return this
    }






}
