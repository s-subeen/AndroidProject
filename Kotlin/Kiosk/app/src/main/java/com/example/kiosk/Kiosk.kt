package com.example.kiosk

import java.text.DecimalFormat


const val BURGER = "burger"
const val FRIED = "fried"
const val BEVERAGE = "beverage"
fun main() {
    val productManage = ProductManage()
    val mainProduct = MainProduct()
    val cart = Cart()
    val bank = Bank()

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
            1 -> {
                productManage.displayMenu(BURGER)

                while (true) {
                    val enter = readln()
                    var number: Int
                    if (enter.isNumber()) {
                        number = enter.toInt()
                    } else {
                        continue
                    }

                    if (number == 0) {
                        break
                    } else if (number < 0 || number > productManage.getMenuCount(BURGER)) {
                        println("잘 못 된 번호를 입력했어요. 다시 입력해주세요.")
                    } else {
                        val item = productManage.getSelectedItem(BURGER, number)
                        if (cart.checkAddItem(item)) {
                            cart.addItem(item)
                        }
                        break
                    }
                }
            }

            2 -> {
                productManage.displayMenu(FRIED)

                while (true) {
                    val enter = readln()
                    var number: Int
                    if (enter.isNumber()) {
                        number = enter.toInt()
                    } else {
                        continue
                    }

                    if (number == 0) {
                        break
                    } else if (number < 0 || number > productManage.getMenuCount(FRIED)) {
                        println("잘 못 된 번호를 입력했어요. 다시 입력해주세요.")
                    } else {
                        val item = productManage.getSelectedItem(FRIED, number)
                        if (cart.checkAddItem(item)) {
                            cart.addItem(item)
                        }
                        break
                    }
                }
            }

            3 -> {
                productManage.displayMenu(BEVERAGE)

                while (true) {
                    val enter = readln()
                    var number: Int
                    if (enter.isNumber()) {
                        number = enter.toInt()
                    } else {
                        continue
                    }

                    if (number == 0) {
                        break
                    } else if (number < 0 || number > productManage.getMenuCount(BEVERAGE)) {
                        println("잘 못 된 번호를 입력했어요. 다시 입력해주세요.")
                    } else {
                        val item = productManage.getSelectedItem(BEVERAGE, number)
                        if (cart.checkAddItem(item)) {
                            cart.addItem(item)
                        }
                        break
                    }
                }
            }

            4 -> {  // Order
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

