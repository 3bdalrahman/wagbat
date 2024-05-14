package com.example.wagbat

data class UserData(
    val id: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val password: String? = null
){
    constructor(): this("","","","")
}
