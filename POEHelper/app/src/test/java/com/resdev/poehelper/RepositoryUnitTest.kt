package com.resdev.poehelper

import com.resdev.poehelper.model.Config
import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class RepositoryUnitTest {
    lateinit var model: ItemsModel
    init {
        runBlocking {
            model = PoeNinjaLoading.loadItems("Standard", "Scarab", "en")
            model.bindModel()
        }
    }


    @Test fun isDefaultModelIsInitialized(){
        assert(ItemsModel.emptyModel!=null)
    }

    @Test fun isItemModelIsNotEmpty(){
        assert(model.lines.isNotEmpty())
    }

    @Test fun isItemIsInitialized(){
        assert(model.lines[0].name.isNotEmpty())
    }

    @Test fun isItemModelReferenceIsInitialized(){
        assert(model.lines[0].itemsModel!=null)
    }


}