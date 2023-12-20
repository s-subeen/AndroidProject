package com.example.kiosk


import java.time.LocalDateTime

class Bank {
    private val now = LocalDateTime.now()
    private val start = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 20, 0, 0)
    private val end = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 20, 40, 0)
    private var amount: Int = 0

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

        return if (getStatusPayable()) {
            println("현재 시각은 ${now.hour}시 ${now.minute}분입니다.")
            println("은행 점검 시간은 ${start.hour}시 ${start.minute}분 ~ ${end.hour}시 ${end.minute}분이므로 결제할 수 없습니다.")
            false
        } else if (tempAmount < 0) {
            println("현재 잔액은 ${decimalFormat(amount)}W 으로 ${decimalFormat(money - amount)}W이 부족해서 주문할 수 없습니다.")
            false
        } else {
            amount -= money
            println("결제를 완료했습니다.")
            true
        }
    }

    private fun getStatusPayable(): Boolean =
        now.toLocalTime() >= start.toLocalTime() && now.toLocalTime() <= end.toLocalTime()

}