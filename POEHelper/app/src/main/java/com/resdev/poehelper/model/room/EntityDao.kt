package com.resdev.poehelper.model.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EntityDao{

    @Query("Select * from ItemEntity")
    fun getItems():LiveData<List<ItemEntity>>

    @Query("Select distinct itemType from ItemEntity")
    fun getTypes():List<String>

    @Update
    fun updateItem(item: ItemEntity)

    @Delete
    fun removeItem(item: ItemEntity)

    @Insert
    fun insertItem(item: ItemEntity)
}