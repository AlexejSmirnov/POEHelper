package com.resdev.poehelper.view.activity

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.resdev.poehelper.R
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.model.retrofit.PoeMarket
import com.resdev.poehelper.repository.PreloadingRepository
import com.resdev.poehelper.MyApplication
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*

class ActivityUtil {

    companion object{
        fun createCurrencyPicker(context: Context, callback: ()->Unit){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle(context.resources.getString(R.string.choose_currency))
            var arrays: Array<Array<String>> = CurrentValue.getArray() ?: throw Exception()
            builder.setItems(arrays[1]
            ) { dialog, which ->
                val text = arrays[0][which]
                Config.setCurrency(text)
                CurrentValue.getActualData()
                callback()
            }
            builder.show()
        }


        fun createLeaguePicker(context: Context, callback: () -> Unit){
            runBlocking {
                launch {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setTitle(context.resources.getString(R.string.choose_league))
                    builder.setItems(
                        PreloadingRepository.league.getCompleted()
                    ) { dialog, which ->
                        val lv: ListView =
                            (dialog as AlertDialog).listView
                        Config.setLeague(lv.getItemAtPosition(which).toString())
                        callback()
                    }
                    builder.show()
                }
            }
        }

        fun createColorPicker(context: Context, callback: () -> Unit){
            val dialog = AmbilWarnaDialog(
                context,
                Config.getColor(),
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

        fun createLanguagePicker(context: Context, callback: () -> Unit){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle(context.resources.getString(R.string.change_language))
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

        fun setLang(activityContext: Context){
            val activityRes: Resources = activityContext.resources
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