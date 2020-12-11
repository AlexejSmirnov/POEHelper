package com.resdev.poehelper.repository

import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ApplicationDatabase
import com.resdev.poehelper.model.room.ItemEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val poeNinjaLoading: PoeNinjaLoading, private val database: ApplicationDatabase, private val config: Config){//
    //val database = ApplicationDatabase.getInstance(MyApplication.getApplicationContext())

    suspend fun getItem(itemName: String, league: String = config.getLeague(), language: String = config.getLanguage()): ItemsModel = poeNinjaLoading.loadItems(league, itemName, language)

    suspend fun updateItem(item: ItemEntity) = database.entityDao.updateItem(item)

    suspend fun getItemsFromDatabase(): List<ItemEntity> = database.entityDao.getItems()

    suspend fun addItem(item: ItemEntity) = database.entityDao.insertItem(item)

    suspend fun removeEntity(item: ItemEntity) = database.entityDao.removeItem(item)

    suspend fun getTypes(): List<String> = database.entityDao.getTypes()
}