package com.resdev.poehelper.repository

import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ApplicationDatabase
import com.resdev.poehelper.model.room.ItemEntity
import com.resdev.poehelper.MyApplication

object ItemRepository{
    private val database = ApplicationDatabase.getInstance(MyApplication.getApplicationContext())

    suspend fun getItem(itemName: String) = PoeNinjaLoading.loadItems(Config.getLeague(), itemName)

    suspend fun updateItem(item: ItemEntity) = database.entityDao.updateItem(item)

    suspend fun getItemsFromDatabase() = database.entityDao.getItems()

    suspend fun addItem(item: ItemEntity) = database.entityDao.insertItem(item)

    suspend fun removeEntity(item: ItemEntity) = database.entityDao.removeItem(item)

    suspend fun getTypes() = database.entityDao.getTypes()
}