package com.resdev.poehelper

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import com.resdev.poehelper.model.room.ApplicationDatabase
import com.resdev.poehelper.model.room.EntityDao
import com.resdev.poehelper.utils.fromRetrofitItemToRoomEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var userDao: EntityDao
    private lateinit var db: ApplicationDatabase
    private lateinit var itemsModel: ItemsModel
    private val type = "Scarab"
    private val league = "Standard"
    private val lang = "en"

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ApplicationDatabase::class.java).build()
        userDao = db.entityDao
        runBlocking {
            itemsModel = PoeNinjaLoading.loadItems(league, type, lang)
            itemsModel.bindModel()
        }
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun checkIfListsOfModifiersIsEqual() {
        val itemEntity = fromRetrofitItemToRoomEntity(itemsModel.lines[0], type)
        runBlocking {
            userDao.insertItem(itemEntity)
            assertEquals(userDao.getItems()[0].implicitModifiers, itemEntity.implicitModifiers)
            assertEquals(userDao.getItems()[0].explicitModifiers, itemEntity.explicitModifiers)
        }

    }


}