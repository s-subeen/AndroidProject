package com.example.kiosk

import java.time.LocalDateTime

class Bank {
    private var amount: Int = 0
    private val dateAndTime: LocalDateTime by lazy {
        LocalDateTime.now()
    }

    init {
        val initAmount = (10000..30000).random()
        inBalance(initAmount)
    }

    private fun inBalance(money: Int): Boolean {
        amount += money
        return true
    }

    fun outBalance(money: Int): Boolean {
        val tempAmount = amount - money
        return if (tempAmount < 0) {
            println("현재 잔액은 ${decimalFormat(amount)}W 으로 ${decimalFormat(money - amount)}W이 부족해서 주문할 수 없습니다.")
            false
        } else {
            amount -= money
            println("결제를 완료했습니다.")
            true
        }
    }
}