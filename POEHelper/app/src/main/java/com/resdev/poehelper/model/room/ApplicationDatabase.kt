package com.resdev.poehelper.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemEntity::class, ExplicitModifier::class, ImplicitModifier::class, Sparkline::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val entityDao: EntityDao

    companion object{
        private lateinit var INSTANCE: ApplicationDatabase
        fun getInstance(context: Context): ApplicationDatabase{
            if (!::INSTANCE.isInitialized){
                INSTANCE = Room.databaseBuilder(context, ApplicationDatabase::class.java, "database").build()
            }
            return INSTANCE

        }
    }
}