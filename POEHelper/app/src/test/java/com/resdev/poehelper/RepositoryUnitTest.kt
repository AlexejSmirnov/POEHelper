package com.resdev.poehelper

import androidx.test.runner.AndroidJUnit4
import com.resdev.poehelper.di.DaggerTestComponent
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.repository.ItemRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class RepositoryUnitTest {
    @Inject lateinit var repository: ItemRepository
    lateinit var model: ItemsModel
    init {
        runBlocking {
            DaggerTestComponent.create().inject(this@RepositoryUnitTest)
            model = repository.getItem("Scarab", "Standard", "en")
            model.bindModel()
        }
    }


    @Test fun isItemModelIsNotEmpty(){
        assert(model.lines.isNotEmpty())
    }

    @Test fun isItemIsInitialized(){
        assert(model.lines[0].name.isNotEmpty())
    }

    @Test fun isItemModelReferenceIsInitialized(){
        assert(model.lines[0].itemsModel != ItemsModel.emptyModel)
    }


}