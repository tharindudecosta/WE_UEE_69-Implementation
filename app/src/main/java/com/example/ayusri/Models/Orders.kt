package com.example.ayusri.Models

data class Orders(
    val orderId: String? = null,

    var prodId: String? = null,
    var prodName: String? = null,
    var prodPrice: String? = null,
    var prodDesc: String? = null,

    var sellerName: String? = null,
    var sellerShop: String? = null,
    var sellerTel: String? = null,

    var amountOrdered: String? = null,
    var customerId: String? = null

)
