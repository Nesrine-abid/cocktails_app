package com.example.cocktails_app.core.service

import com.example.prjets9.core.model.Category

class CategoriesFetcher {

    //API route
    //Get hh/gh/list.php?c=list
    fun fetchCategories (success: (List<Category>) -> Unit, failure: (Error) -> Unit){
        success(emptyList())
    }
}