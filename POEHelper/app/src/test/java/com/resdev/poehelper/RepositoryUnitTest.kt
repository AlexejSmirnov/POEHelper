package com.resdev.poehelper

import com.resdev.poehelper.model.pojo.ItemsModel
import com.resdev.poehelper.model.retrofit.PoeNinjaLoading
import org.junit.Test

class RepositoryUnitTest {
    var model = PoeNinjaLoading.loadItems("Standard", "Scarab")

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