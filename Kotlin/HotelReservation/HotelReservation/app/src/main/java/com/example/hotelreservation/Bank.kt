package com.example.hotelreservation

import kotlin.random.Random

class Bank {
    init {

    }

    private fun random(): Int {
        return Random.nextInt(50000, 100000)
    }
}
