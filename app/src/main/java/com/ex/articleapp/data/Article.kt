package com.ex.articleapp.data

data class Article(val title : String,val about : String,val explanation : String,val publisher : String) {
    constructor():this("","","","")
}