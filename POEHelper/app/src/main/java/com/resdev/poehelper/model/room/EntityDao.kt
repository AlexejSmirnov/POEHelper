package com.resdev.poehelper.model.room

import androidx.room.*

@Dao
abstract class EntityDao{


    suspend fun getItems():List<ItemEntity>{
        var items = _getItems()
        for (i: ItemEntity in items){

            i.explicitModifiers = getExplicitModifier(i.id)
            i.implicitModifiers = getImplicitModifier(i.id)
        }
        return items
    }

    suspend fun updateItem(item: ItemEntity){
        _updateItem(item)
        for (i: ImplicitModifier in item.implicitModifiers){updateImplicitModifier(i)}
        for (i: ExplicitModifier in item.explicitModifiers){updateExplicitModifier(i)}
    }
    suspend fun removeItem(item: ItemEntity){
        _removeItem(item)
        for (i: ImplicitModifier in item.implicitModifiers){removeImplicitModifier(i)}
        for (i: ExplicitModifier in item.explicitModifiers){removeExplicitModifier(i)}
    }
    suspend fun insertItem(item: ItemEntity){
        _insertItem(item)
        for (i: ImplicitModifier in item.implicitModifiers){insertImplicitModifier(i)}
        for (i: ExplicitModifier in item.explicitModifiers){insertExplicitModifier(i)}
    }

    //ItemEntity
    @Query("Select * from ItemEntity")
    abstract suspend fun _getItems():List<ItemEntity>

    @Query("Select distinct itemType from ItemEntity")
    abstract suspend fun getTypes():List<String>

    @Update
    abstract suspend fun _updateItem(item: ItemEntity)

    @Delete
    abstract suspend fun _removeItem(item: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun _insertItem(item: ItemEntity)


    //ExplicitModifier
    @Query("Select * from ExplicitModifier WHERE itemId = :id")
    protected abstract suspend fun getExplicitModifier(id: Int): List<ExplicitModifier>

    @Update
    abstract suspend fun updateExplicitModifier(item: ExplicitModifier)

    @Delete
    abstract suspend fun removeExplicitModifier(item: ExplicitModifier)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertExplicitModifier(item: ExplicitModifier)

    //ImplicitModifier
    @Query("Select * from ImplicitModifier WHERE itemId = :id")
    protected abstract suspend fun getImplicitModifier(id: Int): List<ImplicitModifier>

    @Update
    abstract suspend fun updateImplicitModifier(item: ImplicitModifier)

    @Delete
    abstract suspend fun removeImplicitModifier(item: ImplicitModifier)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertImplicitModifier(item: ImplicitModifier)


}