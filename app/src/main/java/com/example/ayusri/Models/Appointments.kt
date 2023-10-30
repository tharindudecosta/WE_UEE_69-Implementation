package com.example.ayusri.Models

data class Appointments(

    var appointmentId: String? = null,

    var docId: String? = null,
    var docName: String? = null,
    var docEmail: String? = null,
    var docPhone: String? = null,
    var docAddress: String? = null,

    var cusId: String? = null,
    var cusName: String? = null,
    var ailment: String? = null,
    var date: String? = null,
    var time: String? = null
)