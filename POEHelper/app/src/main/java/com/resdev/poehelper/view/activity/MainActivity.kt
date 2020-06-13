package com.resdev.poehelper.view.activity


import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.annotation.ColorInt
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.resdev.poehelper.Config
import com.resdev.poehelper.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.Util.isColorLight
import com.resdev.poehelper.Util.showInternetConnectionError
import com.resdev.poehelper.model.retrofit.PoeMarket.rebuildRetrofit
import com.resdev.poehelper.repository.Repository
import com.resdev.poehelper.view.fragment.BookmarkFragment
import com.resdev.poehelper.view.fragment.CurrencyFragment
import com.resdev.poehelper.view.fragment.ItemFragment
import com.resdev.poehelper.view.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yuku.ambilwarna.AmbilWarnaDialog
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
private lateinit var fragment: MainFragment
private val APP_PREFERENCES = "mysettings"
private var lastFragmentMenuId: Int = R.id.nav_currency
private var isBookmarkOpened = false
private var mSettings: SharedPreferences? = null
private lateinit var bookmarkItem: MenuItem
private lateinit var searchItem: MenuItem
private var bookmarkIconClosed: Drawable? = null
private var bookmarkIconOpened : Drawable? = null
private var leagues = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        loadData()
        setLang()
        setupSettings()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = Config.league
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            0, 0
        )
        bookmarkIconClosed = getDrawable(R.drawable.ic_star_border_white_24dp)
        bookmarkIconOpened = getDrawable(R.drawable.ic_star_white_24dp)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        openFragment(lastFragmentMenuId)
        navigation_view.itemIconTintList = null
        navigation_view.setNavigationItemSelectedListener {
            toolbar.collapseActionView()
            isBookmarkOpened = false
            bookmarkItem.icon = bookmarkIconClosed
            openFragment(it.itemId)
            return@setNavigationItemSelectedListener true
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        searchItem = menu?.findItem(R.id.app_bar_search)!!
        bookmarkItem = menu?.findItem(R.id.switch_fragments)!!
        paintInterface(Config.color)
        var searchView = searchItem!!.actionView as SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return try{
                    fragment.setFilter(query)
                    true
                } catch (ex : Exception){
                    false
                }
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return try{
                    fragment.setFilter(newText)
                    true
                } catch (ex : Exception){
                    false
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.currency_picker -> {
                if (!CurrentValue.isInitialized()){
                    showInternetConnectionError(frameLayout)
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
                    CurrentValue.getActualData()
                    fragment.notifyCurrencyChanged()
                }
                builder.show()
                true
            }
            R.id.league_picker -> {
                if (leagues.isEmpty()){
                    showInternetConnectionError(frameLayout)
                    return false
                }
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)

                builder.setTitle(resources.getString(R.string.choose_league))
                builder.setItems(leagues.toTypedArray()
                ) { dialog, which ->
                    val lv: ListView =
                        (dialog as AlertDialog).listView
                    Config.league = lv.getItemAtPosition(which).toString()
                    Repository.updateExchange()
                    title = Config.league
                    if (::fragment.isInitialized){
                    fragment.notifyLeagueChanged()
                    }

                }
                builder.show()
        true
            }
            R.id.color_picker ->{
                val bar = findViewById<Toolbar>(R.id.toolbar)
                val dialog = AmbilWarnaDialog(
                    this,
                    Config.color,
                    object : AmbilWarnaDialog.OnAmbilWarnaListener {
                        override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                            Config.color = color
                            this@MainActivity.paintInterface(color)
                            if (::fragment.isInitialized){
                                fragment.paintRecycler()
                            }

                        }

                        override fun onCancel(dialog: AmbilWarnaDialog?) {
                        }
                    })
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
                ) { _, which ->
                    Config.language =languages[which]
                    Repository.updateExchange()
                    this@MainActivity.recreate()
                }
                builder.show()

                true
            }
            R.id.switch_fragments->{
                if (!CurrentValue.isInitialized()){
                    showInternetConnectionError(frameLayout)
                    return true
                }
                fragment = BookmarkFragment()
                isBookmarkOpened = !isBookmarkOpened
                if (isBookmarkOpened)
                    bookmarkItem.icon = bookmarkIconOpened
                else{
                    bookmarkItem.icon = bookmarkIconClosed
                }
                openFragment(lastFragmentMenuId)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }


    fun paintInterface(color : Int){
        toolbar.setBackgroundColor(color)
        setTextColor(color)
        this.window.statusBarColor = darkenColor(color)
        if ((Build.VERSION.SDK).toInt()>=26){
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

    fun setTextColor(color: Int){
        var color = Integer.toHexString(color)
        if (isColorLight(color))
        {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_menu_black_24dp)
            paintElements(resources.getColor(R.color.black))
        }
        else{
            window.decorView.systemUiVisibility = 0
            toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_menu_white_24dp)
            paintElements(resources.getColor(R.color.white))
        }
        if (isBookmarkOpened)
            bookmarkItem.icon = bookmarkIconOpened
        else{
            bookmarkItem.icon = bookmarkIconClosed
        }
    }


    private fun paintElements(color: Int){
        var editText = searchItem.actionView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val mCollapseIcon: Field = toolbar.javaClass.getDeclaredField("mCollapseIcon")
        mCollapseIcon.isAccessible = true
        val drw: Drawable = mCollapseIcon.get(toolbar) as Drawable
        val searchIcon = getDrawable(R.drawable.ic_search_white_24dp)
        toolbar.overflowIcon?.setTint(color)
        (searchItem.actionView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView).drawable.setTint(color)
        drw.setTint(color)
        toolbar.setTitleTextColor(color)
        editText.setTextColor(color)
        editText.setHintTextColor(color)
        searchIcon?.setTint(color)
        bookmarkIconClosed?.setTint(color)
        bookmarkIconOpened?.setTint(color)
        searchItem.icon = searchIcon
    }

    fun saveData(){
        val editor: SharedPreferences.Editor = mSettings!!.edit()
        editor.putString("League", Config.league)
        editor.putString("Currency", Config.currency)
        editor.putString("Language", Config.language)
        editor.putInt("Color", Config.color)
        editor.putInt("LastFragment", lastFragmentMenuId)
        editor.putBoolean("isBookmarkOpened", isBookmarkOpened)
        editor.apply()
    }

    fun loadData(){
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        Config.league = mSettings!!.getString("League", "Standard")!!
        Config.currency = mSettings!!.getString("Currency", "Chaos Orb")!!
        Config.language = mSettings!!.getString("Language", "en")!!
        Config.color = mSettings!!.getInt("Color", -0x000001)
        lastFragmentMenuId = mSettings!!.getInt("LastFragment", R.id.nav_currency)
        isBookmarkOpened = mSettings!!.getBoolean("isBookmarkOpened", false)
    }


    fun setupSettings(){
        CurrentValue
        Repository.loadLeagues(leagues)
    }

    fun setLang(){
        val activityRes: Resources = resources
        val activityConf: Configuration = activityRes.configuration
        var lang = Config.language
        if (lang == "ge"){
            lang = "de"
        }
        val newLocale = Locale(lang)
        activityConf.setLocale(newLocale)
        activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)

        val applicationRes: Resources = applicationContext.resources
        val applicationConf: Configuration = applicationRes.configuration
        applicationConf.setLocale(newLocale)
        applicationRes.updateConfiguration(
            applicationConf,
            applicationRes.getDisplayMetrics()
        )
        rebuildRetrofit()

    }


    fun openFragment(navigationItemId: Int){
        GlobalScope.launch {
            while (!CurrentValue.isInitialized()){ }
            withContext(Dispatchers.Main){

                fragment = ItemFragment()
                var bundle = Bundle()
                when (navigationItemId){
                    R.id.nav_currency -> {
                        fragment=CurrencyFragment()
                        bundle.putString("Value","Currency")

                    }
                    R.id.nav_fragment ->{
                        fragment=CurrencyFragment()
                        bundle.putString("Value","Fragment")

                    }
                    R.id.nav_delirium_orb ->bundle.putString("Value","DeliriumOrb")
                    R.id.nav_watchstone ->bundle.putString("Value","Watchstone")
                    R.id.nav_oil ->bundle.putString("Value","Oil")
                    R.id.nav_incubator ->bundle.putString("Value","Incubator")
                    R.id.nav_scarab ->bundle.putString("Value","Scarab")
                    R.id.nav_fossil ->bundle.putString("Value","Fossil")
                    R.id.nav_essence ->bundle.putString("Value","Essence")
                    R.id.nav_resonator ->bundle.putString("Value","Resonator")
                    R.id.nav_divination_card ->bundle.putString("Value","DivinationCard")
                    R.id.nav_skill_gem ->bundle.putString("Value","SkillGem")
                    R.id.nav_base_type ->bundle.putString("Value","BaseType")
                    R.id.nav_helmet_enchant ->bundle.putString("Value","HelmetEnchant")
                    R.id.nav_map ->bundle.putString("Value","Map")
                    R.id.nav_unique_armour ->bundle.putString("Value","UniqueArmour")
                    R.id.nav_unique_flask ->bundle.putString("Value","UniqueFlask")
                    R.id.nav_unique_weapon ->bundle.putString("Value","UniqueWeapon")
                    R.id.nav_unique_accessory ->bundle.putString("Value","UniqueAccessory")
                    R.id.nav_unique_jewel ->bundle.putString("Value","UniqueJewel")
                    R.id.nav_prophecy ->bundle.putString("Value","Prophecy")
                    R.id.nav_unique_map ->bundle.putString("Value","UniqueMap")
                    R.id.nav_beast ->bundle.putString("Value","Beast")
                    R.id.nav_vial->bundle.putString("Value","Vial")

                }
                if (isBookmarkOpened){
                    fragment = BookmarkFragment()
                }
                lastFragmentMenuId = navigationItemId
                (fragment as Fragment).arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment as Fragment).commit()
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
    }








}
