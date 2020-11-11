package com.resdev.poehelper.view.activity


import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.R
import com.resdev.poehelper.utils.Util.showInternetConnectionError
import com.resdev.poehelper.utils.ColorsUtil.getDarkenColor
import com.resdev.poehelper.utils.ColorsUtil.isColorLight
import com.resdev.poehelper.view.fragment.BookmarkFragment
import com.resdev.poehelper.view.fragment.CurrencyFragment
import com.resdev.poehelper.view.fragment.ItemFragment
import com.resdev.poehelper.view.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.reflect.Field


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

    override fun onCreate(savedInstanceState: Bundle?) {
        loadData()
        ActivityUtil.setLang(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = Config.getLeague()
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            0, 0
        )

        bookmarkIconClosed = getDrawable(R.drawable.ic_star_border_white_24dp)
        bookmarkIconOpened = getDrawable(R.drawable.ic_star_white_24dp)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        openFragmentWithCheck(lastFragmentMenuId)
        navigation_view.itemIconTintList = null
        navigation_view.setNavigationItemSelectedListener {
            toolbar.collapseActionView()
            isBookmarkOpened = false
            bookmarkItem.icon = bookmarkIconClosed
            openFragmentWithCheck(it.itemId)
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        searchItem = menu?.findItem(R.id.app_bar_search)!!
        bookmarkItem = menu?.findItem(R.id.switch_fragments)!!
        paintInterface(Config.getColor())
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
                try{
                    ActivityUtil.createCurrencyPicker(this){}
                }
                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                    showInternetConnectionError(frameLayout)
                }
                true
            }
            R.id.league_picker -> {
                try {
                    ActivityUtil.createLeaguePicker(this){
                        title = Config.getLeague()
                    }
                }
                catch (ex: java.lang.Exception){
                    ex.printStackTrace()
                    showInternetConnectionError(frameLayout)
                }
                true
            }
            R.id.color_picker ->{
                ActivityUtil.createColorPicker(this){
                    this@MainActivity.paintInterface(Config.getColor())
                }
                return true}
            R.id.language_picker -> {
                ActivityUtil.createLanguagePicker(this){
                    this@MainActivity.recreate()
                }
                true
            }
            R.id.switch_fragments->{
                try {
                    fragment = BookmarkFragment()
                    isBookmarkOpened = !isBookmarkOpened
                    switchBookmarkIcons()
                    openFragmentWithCheck(lastFragmentMenuId)
                }
                catch (ex: java.lang.Exception){
                    showInternetConnectionError(frameLayout)
                }
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
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getDarkenColor(color)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.colorMode = color
        }
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
        switchBookmarkIcons()
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
        editor.putInt("LastFragment", lastFragmentMenuId)
        editor.putBoolean("isBookmarkOpened", isBookmarkOpened)
        editor.apply()
        Config.saveConfigs()
    }

    fun loadData(){
        CurrentValue
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        lastFragmentMenuId = mSettings!!.getInt("LastFragment", R.id.nav_currency)
        isBookmarkOpened = mSettings!!.getBoolean("isBookmarkOpened", false)
        Config.loadConfig()
    }

    fun openFragmentWithCheck(navigationItemId: Int){
        GlobalScope.launch {
            while (!CurrentValue.isCurrentDataIsReady()){

            }
            withContext(Dispatchers.Main){
                openFragment(navigationItemId)
            }
        }
    }

    private fun openFragment(navigationItemId: Int){
        fragment = ItemFragment()
        var bundle = Bundle()
        if (navigationItemId==R.id.nav_fragment || navigationItemId==R.id.nav_currency){
            fragment = CurrencyFragment()
        }
        if (isBookmarkOpened){
            fragment = BookmarkFragment()
        }
        bundle.putInt("Value", navigationItemId)
        lastFragmentMenuId = navigationItemId
        (fragment as Fragment).arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment as Fragment).commit()
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    fun switchBookmarkIcons(){
            if (isBookmarkOpened)
                bookmarkItem.icon = bookmarkIconOpened
            else{
                bookmarkItem.icon = bookmarkIconClosed
            }

    }
}
