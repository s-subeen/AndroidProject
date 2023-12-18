package com.example.hotelreservation

import java.time.LocalDate

data class BookingList(
    val people: People,
    val room: Int,
    val checkIn: LocalDate,
    val checkOut: LocalDate
)
