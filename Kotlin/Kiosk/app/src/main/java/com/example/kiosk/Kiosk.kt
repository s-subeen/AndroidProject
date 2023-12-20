package com.example.kiosk

import java.text.DecimalFormat

const val BURGER = "burger"
const val FRIED = "fried"
const val BEVERAGE = "beverage"

val productManager = ProductManager()
val mainProduct = MainProduct()
val cart = Cart()
val bank = Bank()

fun main() {
    while (true) {
        mainProduct.displayMain()
        val input = readln()
        var select: Int
        if (input.isNumber()) {
            select = input.toInt()
        } else {
            continue
        }

        mainProduct.displayTitle(select)

        when (select) {
            1, 2, 3 -> processMenu(getCategoryByNumber(select))
            4 -> {
                if (cart.displayCart()) {
                    bank.outBalance(cart.getTotal())
                }
            }
            5 -> {
                println("프로그램을 종료합니다.")
                break
            }
            else -> {
                println("잘 못 된 번호를 입력했어요. 다시 입력해주세요.")
            }
        }
    }
}

fun processMenu(category: String) {
    productManager.displayMenu(category)

    while (true) {
        val number = getUserInputAsNumber()

        if (number == 0) {
            break
        } else if (number < 0 || number > productManager.getMenuCount(category)) {
            println("잘 못 된 번호를 입력했어요. 다시 입력해주세요.")
        } else {
            val item = productManager.getSelectedItem(category, number)
            if (cart.checkAddItem(item)) {
                cart.addItem(item)
            }
            break
        }
    }
}

fun getCategoryByNumber(select: Int): String {
    return when (select) {
        1 -> BURGER
        2 -> FRIED
        3 -> BEVERAGE
        else -> throw IllegalArgumentException("Invalid category number: $select")
    }
}

fun getUserInputAsNumber(): Int {
    while (true) {
        val input = readln()
        if (input.isNumber()) {
            return input.toInt()
        } else {
            println("잘 못 된 번호를 입력했어요. 다시 입력해주세요.")
        }
    }
}

fun String.isNumber(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        println("잘 못 된 문자 타입을 입력했어요. 다시 입력해 주세요.")
        false
    }
}

fun decimalFormat(price: Int): String {
    val dec = DecimalFormat("#,###")
    return dec.format(price)
}
