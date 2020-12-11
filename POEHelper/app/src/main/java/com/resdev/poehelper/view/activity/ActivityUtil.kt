package com.resdev.poehelper.view.activity

import android.content.res.Configuration
import android.content.res.Resources
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.resdev.poehelper.MyApplication
import com.resdev.poehelper.R
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
                this.config.setCurrency(text)
                currentValue.getActualData()
                callback()
            }
            builder.show()
        }


        fun MainActivity.createLeaguePicker(callback: () -> Unit){
            val context = this
            val config = config
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
                        config.setColor(color)
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
                config.setLanguage(languages[which])
                callback()
            }
            builder.show()
        }

        fun MainActivity.setLang(){
            val activityRes: Resources = this.resources
            val activityConf: Configuration = activityRes.configuration
            var lang = config.getLanguage()
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