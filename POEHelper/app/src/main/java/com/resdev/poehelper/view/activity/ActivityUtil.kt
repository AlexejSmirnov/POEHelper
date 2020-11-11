package com.resdev.poehelper.view.activity

import android.content.Context
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.resdev.poehelper.R
import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.CurrentValue
import com.resdev.poehelper.repository.Repository
import com.resdev.poehelper.repository.Repository.loadLeagues
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import yuku.ambilwarna.AmbilWarnaDialog

class ActivityUtil {

    companion object{
        fun createCurrencyPicker(context: Context, callback: ()->Unit){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle(context.resources.getString(R.string.choose_currency))
            var arrays = CurrentValue.getArray()
            builder.setItems(arrays[1]
            ) { dialog, which ->
                val text = arrays[0][which]
                Config.setCurrency(text)
                CurrentValue.getActualData()
                callback()
            }
            builder.show()
            true
        }
        private var lastLeagues: Array<String>? = null

        fun createLeaguePicker(context: Context, callback: () -> Unit){
            runBlocking {
                launch {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setTitle(context.resources.getString(R.string.choose_league))
                    if (lastLeagues==null){
                        lastLeagues = loadLeagues().await()
                    }
                    builder.setItems(
                        lastLeagues
                    ) { dialog, which ->
                        val lv: ListView =
                            (dialog as AlertDialog).listView
                        Config.setLeague(lv.getItemAtPosition(which).toString())
                        Repository.updateExchange()
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
                Repository.updateExchange()
                callback()
            }
            builder.show()
        }
    }
}