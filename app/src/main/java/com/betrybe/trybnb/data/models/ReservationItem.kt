package com.betrybe.trybnb.data.models

data class BookingDates(
    val checkin: String,
    val checkout: String
)
data class ReservationItem(
    val firstname: String,
    val lastname: String,
    val totalprice: Int,
    val depositpaid: Boolean,
    val bookingdates: BookingDates,
    val additionalneeds: String
)
