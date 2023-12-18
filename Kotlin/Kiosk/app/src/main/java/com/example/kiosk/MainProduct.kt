package com.example.kiosk

class MainProduct {
    private val mainList = ArrayList<MainMenu>()

    init {
        mainList.add(MainMenu(0, "Burger", "주문 즉시 바로 조리해 더욱 맛있는, 맥도날드의 다양한 버거를 소개합니다.", "McDonald's MENU"))
        mainList.add(MainMenu(1, "Side & Dessert", "버거와 함께 즐기면 언제나 맛있는 사이드와 디저트 메뉴!", "McDonald's MENU"))
        mainList.add(MainMenu(2, "Mac Cafe & Beverage", "언제나 즐겁게, 맥카페와 다양한 음료를 부담없이 즐기세요!", "McDonald's MENU"))
        mainList.add(MainMenu(3, "Order", "장바구니를 확인 후 주문합니다.", "ORDER MENU"))
        mainList.add(MainMenu(4, "Cancel", "진행중인 주문을 취소합니다.", "ORDER MENU"))
    }

    fun displayMain() {
        println("\n아래 메뉴판을 보시고 메뉴를 골라 입력해주세요. \n")

        var category = ""
        for (item in mainList) {
            if (category != item.category) {
                category = item.category
                println("[${category}]")
            }
            println("%d. %-20s | %s".format(item.id + 1, item.title, item.explain))
        }
    }

    fun displayTitle(index: Int) {
        val item = mainList.find { it.id == index - 1 }
        if (item == null) {
            return
        } else {
            println("[${item.title} MENU]")
        }
    }
}