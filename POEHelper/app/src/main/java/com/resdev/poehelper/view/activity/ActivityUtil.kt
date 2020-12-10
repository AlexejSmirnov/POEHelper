package com.resdev.poehelper.view.activity

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.R
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.repository.PreloadingRepository
import com.resdev.poehelper.view.activity.ActivityUtil.Companion.createCurrencyPicker
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*

class ActivityUtil {

    companion object{
        fun MainActivity.createCurrencyPicker(callback: ()->Unit){
            val currentValue = MyApplication.getCurrentValue()
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(this.resources.getString(R.string.choose_currency))
            var arrays: Array<Array<String>> = currentValue.getArray() ?: throw Exception()
            builder.setItems(arrays[1]
            ) { _, which ->
                val text = arrays[0][which]
                this.Config.setCurrency(text)
                currentValue.getActualData()
                callback()
            }
            builder.show()
        }


        fun MainActivity.createLeaguePicker(callback: () -> Unit){
            val context = this
            val config = Config
            runBlocking {
                launch {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setTitle(context.resources.getString(R.string.choose_league))
                    builder.setItems(
                        MyApplication.getPreloadingRepository().league.getCompleted()
                    ) { dialog, which ->
                        val lv: ListView =
                            (dialog as AlertDialog).listView
                        config.setLeague(lv.getItemAtPosition(which).toString())
                        callback()
                    }
                    builder.show()
                }
            }
        }

        fun MainActivity.createColorPicker(callback: () -> Unit){
            val dialog = AmbilWarnaDialog(
                this,
                MyApplication.getConfig().getColor(),
                object : AmbilWarnaDialog.OnAmbilWarnaListener {
                    override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                        Config.setColor(color)
                        callback()
                    }
                    override fun onCancel(dialog: AmbilWarnaDialog?) {
                    }
                })
            dialog.show()
        }

        fun MainActivity.createLanguagePicker(callback: () -> Unit){
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(this.baseContext.resources.getString(R.string.change_language))
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
                Config.setLanguage(languages[which])
                callback()
            }
            builder.show()
        }

        fun MainActivity.setLang(){
            val activityRes: Resources = this.resources
            val activityConf: Configuration = activityRes.configuration
            var lang = Config.getLanguage()
            if (lang == "ge"){
                lang = "de"
            }
            val newLocale = Locale(lang)
            activityConf.setLocale(newLocale)
            activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)

            val applicationRes: Resources = MyApplication.getApplicationContext().resources
            val applicationConf: Configuration = applicationRes.configuration
            applicationConf.setLocale(newLocale)
            applicationRes.updateConfiguration(
                applicationConf,
                applicationRes.displayMetrics
            )
        }
    }
}