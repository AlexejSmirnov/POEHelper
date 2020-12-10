package com.resdev.poehelper

import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.repository.ItemRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoryUnitTest {
    lateinit var model: ItemsModel
    val repository = ItemRepository
    init {
        runBlocking {
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