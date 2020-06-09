package com.resdev.poehelper.model.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class EntityDao{


    fun getItems():List<ItemEntity>{
        var items = _getItems()
        for (i: ItemEntity in items){

            i.explicitModifiers = getExplicitModifier(i.id)
            i.implicitModifiers = getImplicitModifier(i.id)
            i.sparkline = getSparkline(i.id)
        }
        return items
    }

    fun updateItem(item: ItemEntity){
        _updateItem(item)
        for (i: ImplicitModifier in item.implicitModifiers){updateImplicitModifier(i)}
        for (i: ExplicitModifier in item.explicitModifiers){updateExplicitModifier(i)}
        updateSparkline(item.sparkline)
    }
    fun removeItem(item: ItemEntity){
        _removeItem(item)
        for (i: ImplicitModifier in item.implicitModifiers){removeImplicitModifier(i)}
        for (i: ExplicitModifier in item.explicitModifiers){removeExplicitModifier(i)}
        removeSparkline(item.sparkline)
    }
    fun insertItem(item: ItemEntity){
        _insertItem(item)
        for (i: ImplicitModifier in item.implicitModifiers){insertImplicitModifier(i)}
        for (i: ExplicitModifier in item.explicitModifiers){insertExplicitModifier(i)}
        insertSparkline(item.sparkline)
    }

    //ItemEntity
    @Query("Select * from ItemEntity")
    abstract fun _getItems():List<ItemEntity>

    @Query("Select distinct itemType from ItemEntity")
    abstract fun getTypes():List<String>

    @Update
    abstract fun _updateItem(item: ItemEntity)

    @Delete
    abstract fun _removeItem(item: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertItem(item: ItemEntity)


    //ExplicitModifier
    @Query("Select * from ExplicitModifier WHERE itemId = :id")
    protected abstract fun getExplicitModifier(id: Int): List<ExplicitModifier>

    @Update
    abstract fun updateExplicitModifier(item: ExplicitModifier)

    @Delete
    abstract fun removeExplicitModifier(item: ExplicitModifier)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertExplicitModifier(item: ExplicitModifier)

    //ImplicitModifier
    @Query("Select * from ImplicitModifier WHERE itemId = :id")
    protected abstract fun getImplicitModifier(id: Int): List<ImplicitModifier>

    @Update
    abstract fun updateImplicitModifier(item: ImplicitModifier)

    @Delete
    abstract fun removeImplicitModifier(item: ImplicitModifier)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImplicitModifier(item: ImplicitModifier)

    //Sparkline
    @Query("Select * from Sparkline WHERE itemId = :id")
    protected abstract fun getSparkline(id: Int): Sparkline

    @Update
    abstract fun updateSparkline(item: Sparkline)

    @Delete
    abstract fun removeSparkline(item: Sparkline)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSparkline(item: Sparkline)
}