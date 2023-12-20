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
            1, 2, 3 -> processMenu(getCategory(select))
            4 -> {
                if (cart.displayCart()) {
                    if (bank.outBalance(cart.getTotal())) { // 결제 성공
                        // TODO 오더 추가
                        cart.clearItem() // 카트 리셋
                    }
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

/*
1) 선택한 카테고리별 메뉴 아이템 목록을 출력
2) 추가로 입력 된 번호에 해당되는 아이템을 출력
3) (2) 아이템을 장바구니에 추가 혹은 뒤로가기
 */
fun processMenu(category: String) {
    productManager.displayMenu(category)

    while (true) {
        val number = getInputNumber()

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

fun getCategory(select: Int): String {
    return when (select) {
        1 -> BURGER
        2 -> FRIED
        3 -> BEVERAGE
        else -> throw IllegalArgumentException("There are no applicable categories.")
    }
}

fun getInputNumber(): Int {
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
