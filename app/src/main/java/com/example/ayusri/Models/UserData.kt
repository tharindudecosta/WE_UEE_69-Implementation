package com.example.ayusri.Models

data class UserData(
    val id:String? = null,
    val fullName:String? = null,
    val email:String? = null,
    val phoneNo:String? = null,
    val password:String? = null,
    val address:String? = null,

    val specialization:String? = null,

    val storeName:String? = null,

    val userType:String? = "Customer",

    var imageUri: String? = null

)
