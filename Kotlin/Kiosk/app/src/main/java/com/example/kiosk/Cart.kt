package com.example.kiosk


class Cart {
    private val cartList = ArrayList<AbstractMenu>()
    private var total: Int = 0
    fun getTotal(): Int = total

    fun addItem(item: AbstractMenu?) {
        if (item == null) {
            println("선택하신 상품이 존재하지 않습니다. 관리자에게 문의해주세요.")
        } else {
            cartList.add(item)
            total += item.price
        }
    }

    fun checkAddItem(item: AbstractMenu?): Boolean {
        if (item == null) {
            println("선택하신 상품이 존재하지 않습니다. 관리자에게 문의해주세요.")
            return false
        } else {
            println(
                "\"${MenuManager(item).displayInfo()}\"\n" +
                        "위 메뉴를 장바구니에 추가하시겠습니까?\n" +
                        "1. 확인    2. 취소"
            )
            while (true) {
                val input = readln()
                var select: Int
                if (input.isNumber()) {
                    select = input.toInt()
                } else {
                    continue
                }
                return when (select) {
                    1 -> {
                        println("${item.name}이(가) 장바구니에 추가되었습니다.")
                        true
                    }

                    2 -> {
                        false
                    }

                    else -> {
                        println("잘 못 된 번호를 입력했어요. 다시 입력해주세요.")
                        continue
                    }
                }
            }
        }
    }

    fun displayCart(): Boolean {
        if (cartList.isEmpty()) {
            println("장바구니가 비어 있습니다.")
            return false
        } else {
            val listGroup = cartList.groupingBy { it }.eachCount()
            for ((item, count) in listGroup) {
                println("${item.displayInfo()} (${count})")
            }

            println(
                "\n[ Total ]\n" +
                        "₩ ${decimalFormat(total)}"
            )

            println("1. 주문    2. 메뉴판")
            while (true) {
                val input = readln()
                var select: Int
                if (input.isNumber()) {
                    select = input.toInt()
                } else {
                    continue
                }
                return when (select) {
                    1 -> {
                        true
                    }

                    2 -> {
                        false
                    }

                    else -> {
                        println("잘 못 된 번호를 입력했어요. 다시 입력해주세요.")
                        continue
                    }
                }
            }
        }
    }

}