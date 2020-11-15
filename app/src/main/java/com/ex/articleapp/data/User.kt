package com.ex.articleapp.data

data class User( val username : String,
                 val fullName : String,
                 val uid: String,
                 val bio: String,
                 val profile_photo: String) {
    constructor(): this("","","","","")
}