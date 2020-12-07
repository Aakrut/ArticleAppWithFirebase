package com.ex.articleapp.data

import com.google.firebase.Timestamp

data class Article(val title : String,val about : String,val explanation : String,val publisher : String,val time : Timestamp) {
    constructor():this("","","","", Timestamp.now())
}