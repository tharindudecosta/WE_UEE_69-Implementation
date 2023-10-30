package com.example.ayusri.Models

class UserGlobal {
    var id:String? = null
    var fullName:String? = null
    var email:String? = null
    var phoneNo:String? = null
    var password:String? = null
    var address:String? = null

    var userType:String? = null

    var specialization:String? = null

    var storeName:String? = null

    var imageUri: String? = null

    companion object {
        private var instance: UserGlobal? = null

        fun getInstance(): UserGlobal {
            if (instance == null) {
                synchronized(UserGlobal::class.java) {
                    if (instance == null) {
                        instance = UserGlobal()
                    }
                }
            }
            return instance!!
        }
    }
}